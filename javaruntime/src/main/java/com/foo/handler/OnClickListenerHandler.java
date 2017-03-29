package com.foo.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/3/16 17:20
 */
public class OnClickListenerHandler implements InvocationHandler {

    private Object targetObject;

    public OnClickListenerHandler(Object obj) {
        targetObject = obj;
    }

    private Map<String, Method> methods = new HashMap<>();

    public void addMethod(String methodName, Method method) {
        methods.put(methodName, method);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Method realMethod = methods.get(methodName);
        System.out.println("----- obj " + targetObject.getClass().getSimpleName() + ", realMethod " + realMethod);
        return realMethod.invoke(targetObject, args);
    }
}
