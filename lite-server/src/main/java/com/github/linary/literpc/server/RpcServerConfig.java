package com.github.linary.literpc.server;

import com.github.linary.literpc.codec.Decoder;
import com.github.linary.literpc.codec.Encoder;
import com.github.linary.literpc.codec.JsonDecoder;
import com.github.linary.literpc.codec.JsonEncoder;
import com.github.linary.literpc.transport.HttpTransportServer;
import com.github.linary.literpc.transport.TransportServer;

import lombok.Data;

/**
 * server配置
 */
@Data
public class RpcServerConfig {

    private Class<? extends TransportServer> transportClass =
            HttpTransportServer.class;
    private Class<? extends Encoder> encoderClass = JsonEncoder.class;
    private Class<? extends Decoder> decoderClass = JsonDecoder.class;

    private int port = 3000;
}
