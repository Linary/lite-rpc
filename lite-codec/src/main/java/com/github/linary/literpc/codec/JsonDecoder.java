package com.github.linary.literpc.codec;

import com.alibaba.fastjson.JSON;

public class JsonDecoder implements Decoder {

    @Override
    public <T> T deocde(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
