package com.github.linary.literpc.server;

import java.lang.reflect.Method;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 表示一个具体服务
 */
@Data
@AllArgsConstructor
public class ServiceInstance {

    private Object target;
    private Method method;
}
