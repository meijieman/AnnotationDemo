package com.foo.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/3/16 17:27
 */
public class Main {

    private Button mButton = new Button();

    public Main() {
//        init();
        initPrint();
    }

    @SuppressWarnings("")
    private void initPrint() {
        // java 动态代理
        // 可以将接口的实现类替换成任意类的任意方法
        try {
            OnClickListenerHandler handler = new OnClickListenerHandler(new Util());
            Method method = Util.class.getMethod("print");
            handler.addMethod("onClick", method);
            Object clickProxy = Proxy.newProxyInstance(OnClickListener.class.getClassLoader(),
                    new Class[]{OnClickListener.class}, handler);
            Method clickMethod = mButton.getClass().getMethod("setOnClickListener", OnClickListener.class);
            clickMethod.invoke(mButton, clickProxy);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            OnClickListenerHandler handler = new OnClickListenerHandler(this);
            Method method = Main.class.getMethod("clickMain");
            handler.addMethod("onClick", method); // 将 OnClick 替换为执行 clickMain
            Object clickProxy = Proxy.newProxyInstance(OnClickListener.class.getClassLoader(),
                    new Class<?>[]{OnClickListener.class}, handler);
            Method clickMethod = mButton.getClass().getMethod("setOnClickListener", OnClickListener.class);
            clickMethod.invoke(mButton, clickProxy);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void clickMain() {
        System.out.println("Button clicked!");
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.mButton.click();
    }
}
