package com.github.linary.literpc.client;

import java.lang.reflect.Proxy;

import com.github.linary.literpc.codec.Decoder;
import com.github.linary.literpc.codec.Encoder;
import com.github.linary.literpc.codec.common.utils.ReflectUtils;

public class RpcClient {

    private RpcClientConfig config;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    public RpcClient() {
        this(new RpcClientConfig());
    }

    public RpcClient(RpcClientConfig config) {
        this.config = config;
        this.encoder = ReflectUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectUtils.newInstance(config.getDecoderClass());
        this.selector = ReflectUtils.newInstance(config.getSelectorClass());

        this.selector.init(config.getPeers(), config.getConnectionCount(),
                           config.getTransportClass());
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(),
                                          new Class[]{clazz},
                                          new RemoteInvoker(clazz, encoder, decoder, selector));
    }
}
