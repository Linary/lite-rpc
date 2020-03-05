package com.github.linary.literpc.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.github.linary.literpc.codec.Decoder;
import com.github.linary.literpc.codec.Encoder;
import com.github.linary.literpc.codec.Request;
import com.github.linary.literpc.codec.Response;
import com.github.linary.literpc.codec.ServiceDescriptor;
import com.github.linary.literpc.transport.TransportClient;

import lombok.extern.slf4j.Slf4j;
import sun.misc.IOUtils;

/**
 * 调用远程服务的代理类
 */
@Slf4j
public class RemoteInvoker implements InvocationHandler {

    private Class clazz;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    public RemoteInvoker(Class clazz, Encoder encoder, Decoder decoder,
                         TransportSelector selector) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
        this.selector = selector;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Request request = new Request();
        request.setService(ServiceDescriptor.from(clazz, method));
        request.setParameters(args);

        Response resp = invokeRemote(request);
        if (resp == null || resp.getCode() != 0) {
            throw new IllegalStateException("fail to invoke remote: " + resp);
        }
        return resp.getData();
    }

    private Response invokeRemote(Request request) {
        TransportClient client = null;
        Response resp;
        try {
            client = selector.select();
            byte[] outBytes = encoder.encode(request);
            InputStream input = client.write(new ByteArrayInputStream(outBytes));
            byte[] inBytes = IOUtils.readFully(input, input.available(), true);
            resp = decoder.deocde(inBytes, Response.class);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            resp = new Response();
            resp.setCode(1);
            resp.setMessage("RpcClient got error: " + e.getClass() + " : " +
                                    e.getMessage());
        } finally {
            if (client != null) {
                selector.release(client);
            }
        }
        return resp;
    }
}
