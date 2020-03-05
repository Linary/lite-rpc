package com.github.linary.literpc.codec;

/**
 * 反序列化
 */
public interface Decoder {

    <T> T deocde(byte[] bytes, Class<T> clazz);
}
