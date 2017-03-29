package com.foo.runtime.inject;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2016/7/15.
 */
public class ViewInjectUtils {

    private static final String TAG = "ViewInjectUtils";

    public static final String FIND_VIEW_BY_ID  = "findViewById";
    public static final String SET_CONTENT_VIEW = "setContentView";

    // 注入主布局
    private static void injectContentView(Activity ac) {
        Class<? extends Activity> clazz = ac.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            int layout = contentView.value();
            try {
                Method method = clazz.getMethod(SET_CONTENT_VIEW, int.class);
                method.setAccessible(true);
                method.invoke(ac, layout);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    // 注入控件
    private static void injectViews(Activity ac) {
        Class<? extends Activity> clazz = ac.getClass();
        Field[] fields = clazz.getDeclaredFields(); // 获取了所有的变量
        for (Field field : fields) {
            ViewInject view = field.getAnnotation(ViewInject.class);
            if (view != null) {
                int viewId = view.value();
                Log.e(TAG, "injectViews: viewId " + viewId);
                if (viewId != -1) {
                    try {
                        Method method = clazz.getMethod(FIND_VIEW_BY_ID, int.class);
                        Object resView = method.invoke(ac, viewId);
                        field.setAccessible(true);
                        field.set(ac, resView); // 将 resView 设置给 field
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void injectEvents(Activity ac) {
        Class<? extends Activity> clazz = ac.getClass();
        Method[] methods = clazz.getMethods(); // 只获取了 非 private 的方法
        for (Method method : methods) {
            // 获取方法上的所有注解
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                // 拿到注解上的注解
                EventBase eventBaseAnnotation = annotationType.getAnnotation(EventBase.class);
                if (eventBaseAnnotation != null) {
                    // 如果为 EventBase
                    String listenerSetter = eventBaseAnnotation.listenerSetter();
                    Class<?> listenerType = eventBaseAnnotation.listenerType();
                    String methodName = eventBaseAnnotation.methodName();
                    try {
                        // 拿到 onClick 注解的 value
                        Method aMethod = annotationType.getDeclaredMethod("value");
                        // 取出 viewId
                        int[] viewIds = (int[])aMethod.invoke(annotation, new Object());
                        // 通过 InvocationHandler 设置代理
                        DynamicHandler handler = new DynamicHandler(ac);
                        handler.addMethod(methodName,method);
                        Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, handler);
                        for (int viewId : viewIds) {
                            View view = ac.findViewById(viewId);
                            Method setEventListenerMethod = view.getClass().getMethod(listenerSetter, listenerType);
                            setEventListenerMethod.invoke(view, listener);
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void inject(Activity ac) {
        injectContentView(ac);
        injectViews(ac);
        injectEvents(ac);
    }

}
