package com.github.linary.literpc.codec;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示RPC的请求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    private ServiceDescriptor service;
    private Object[] parameters;
}
