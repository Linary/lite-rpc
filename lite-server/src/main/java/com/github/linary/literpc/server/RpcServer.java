package com.github.linary.literpc.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.github.linary.literpc.codec.Decoder;
import com.github.linary.literpc.codec.Encoder;
import com.github.linary.literpc.codec.Request;
import com.github.linary.literpc.codec.Response;
import com.github.linary.literpc.codec.common.utils.ReflectUtils;
import com.github.linary.literpc.transport.RequestHandler;
import com.github.linary.literpc.transport.TransportServer;

import lombok.extern.slf4j.Slf4j;
import sun.misc.IOUtils;

@Slf4j
public class RpcServer {

    private RpcServerConfig config;
    private TransportServer server;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    public RpcServer() {
        this(new RpcServerConfig());
    }

    public RpcServer(RpcServerConfig config) {
        this.config = config;
        this.server = ReflectUtils.newInstance(config.getTransportClass());
        this.server.init(config.getPort(), this.handler);
        this.encoder = ReflectUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectUtils.newInstance(config.getDecoderClass());
        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        this.serviceManager.register(interfaceClass, bean);
    }

    public void start() {
        this.server.start();
    }

    public void stop() {
        this.server.stop();
    }

    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream input, OutputStream output) {
            Response resp = new Response();
            try {
                byte[] inBytes = IOUtils.readFully(input, input.available(),
                                                   true);
                Request request = decoder.deocde(inBytes, Request.class);
                log.info("get request: {}", request);
                ServiceInstance instance = serviceManager.lookup(request);
                Object ret = serviceInvoker.invoke(instance, request);
                resp.setData(ret);
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
                resp.setCode(1);
                resp.setMessage("RpcServer got error: " + e.getClass().getName() +
                        " : " + e.getMessage());
            } finally {
                byte[] outBytes = encoder.encode(resp);
                try {
                    output.write(outBytes);
                    log.info("responsed client");
                } catch (IOException e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
    };
}
