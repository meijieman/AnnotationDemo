package com.foo.useinject.inject;

import com.foo.useinject.LogUtil;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/3/15 17:56
 */
public class DynamicHandler implements InvocationHandler{

    private static final String TAG = "DynamicHandler";

    private WeakReference<Object> handlerRef;
    private final HashMap<String, Method> methodMap = new HashMap<>(1);

    public DynamicHandler(Object handler) {
        handlerRef = new WeakReference<>(handler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object handler = handlerRef.get();
        if (handler != null) {
            String methodName = method.getName();
            LogUtil.i(TAG, "invoke: methodName " + methodName);
            method = methodMap.get(methodName);
            if (method != null) {
                LogUtil.i(TAG, "invoke: map " + method.getName());
                return method.invoke(handler, args);
            }
        }

        return null;
    }

    public void addMethod(String name, Method method) {
        methodMap.put(name, method);
    }

    public Object getHandler() {
        return handlerRef.get();
    }

    private void setHandler(Object handler) {
        handlerRef = new WeakReference<>(handler);
    }
}
