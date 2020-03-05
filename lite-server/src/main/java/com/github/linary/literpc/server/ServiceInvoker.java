package com.github.linary.literpc.server;

import com.github.linary.literpc.codec.Request;
import com.github.linary.literpc.codec.common.utils.ReflectUtils;

public class ServiceInvoker {

    public Object invoke(ServiceInstance instance, Request request) {
        return ReflectUtils.invoke(instance.getTarget(), instance.getMethod(),
                                   request.getParameters());
    }
}
