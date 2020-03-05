package com.github.linary.literpc.codec;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 表示网络传输的一个断点
 */
@Data
@AllArgsConstructor
public class Peer {

    private String host;
    private int port;
}
