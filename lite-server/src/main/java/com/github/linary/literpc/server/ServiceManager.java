package com.github.linary.literpc.server;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.linary.literpc.codec.Request;
import com.github.linary.literpc.codec.ServiceDescriptor;
import com.github.linary.literpc.codec.common.utils.ReflectUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 管理RPC暴露的服务
 */
@Slf4j
public class ServiceManager {

    private Map<ServiceDescriptor, ServiceInstance> services;

    public ServiceManager() {
        this.services = new ConcurrentHashMap<>();
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        Method[] methods = ReflectUtils.getPublicMethods(interfaceClass);
        for (Method method : methods) {
            ServiceInstance instance = new ServiceInstance(bean, method);
            ServiceDescriptor descriptor = ServiceDescriptor.from(interfaceClass,
                                                                  method);
            services.put(descriptor, instance);
            log.info("register service: {} {}", descriptor.getClazz(),
                     descriptor.getMethod());
        }
    }

    public ServiceInstance lookup(Request request) {
        ServiceDescriptor descriptor = request.getService();
        return services.get(descriptor);
    }
}
