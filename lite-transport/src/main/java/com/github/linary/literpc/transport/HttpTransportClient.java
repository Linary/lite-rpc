package com.github.linary.literpc.transport;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.github.linary.literpc.codec.Peer;

public class HttpTransportClient implements TransportClient {

    private String url;

    @Override
    public void connect(Peer peer) {
        this.url = "http://" + peer.getHost() + ":" + peer.getPort();
    }

    @Override
    public InputStream write(InputStream data) {
        // 基于短连接实现
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(this.url).openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");

            conn.connect();
            IOUtils.copy(data, conn.getOutputStream());

            int code = conn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                return conn.getInputStream();
            } else {
                return conn.getErrorStream();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }
}
