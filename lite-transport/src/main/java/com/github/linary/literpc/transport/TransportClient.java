package com.github.linary.literpc.transport;

import java.io.InputStream;

import com.github.linary.literpc.codec.Peer;

/**
 * 1. 创建连接
 * 2. 发送数据，并且等待响应
 * 3. 关闭连接
 */
public interface TransportClient {

    void connect(Peer peer);

    InputStream write(InputStream data);

    void close();
}
