package com.github.linary.literpc.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.linary.literpc.codec.Peer;
import com.github.linary.literpc.codec.common.utils.ReflectUtils;
import com.github.linary.literpc.transport.TransportClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RandomTransportSelector implements TransportSelector {

    private List<TransportClient> clients;

    public RandomTransportSelector() {
        clients = new ArrayList<>();
    }

    @Override
    public void init(List<Peer> peers, int count,
                     Class<? extends TransportClient> clazz) {
        count = Math.max(count, 1);
        for (Peer peer : peers) {
            for (int i = 0 ; i < count; i++) {
                TransportClient client = ReflectUtils.newInstance(clazz);
                client.connect(peer);
                clients.add(client);
            }
            log.info("connect server: {}", peer);
        }
    }

    @Override
    public synchronized TransportClient select() {
        int i = new Random().nextInt(clients.size());
        return clients.remove(i);
    }

    @Override
    public synchronized void release(TransportClient client) {
        clients.add(client);
    }

    @Override
    public synchronized void close() {
        for (TransportClient client : clients) {
            client.close();
        }
        clients.clear();
    }
}
