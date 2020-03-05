package com.github.linary.literpc.client;

import java.util.Arrays;
import java.util.List;

import com.github.linary.literpc.codec.Decoder;
import com.github.linary.literpc.codec.Encoder;
import com.github.linary.literpc.codec.JsonDecoder;
import com.github.linary.literpc.codec.JsonEncoder;
import com.github.linary.literpc.codec.Peer;
import com.github.linary.literpc.transport.HttpTransportClient;
import com.github.linary.literpc.transport.TransportClient;

import lombok.Data;

@Data
public class RpcClientConfig {

    private Class<? extends TransportClient> transportClass =
            HttpTransportClient.class;
    private Class<? extends Encoder> encoderClass = JsonEncoder.class;
    private Class<? extends Decoder> decoderClass = JsonDecoder.class;
    private Class<? extends TransportSelector> selectorClass =
            RandomTransportSelector.class;
    private int connectionCount = 1;
    private List<Peer> peers = Arrays.asList(new Peer("127.0.0.1", 3000));
}
