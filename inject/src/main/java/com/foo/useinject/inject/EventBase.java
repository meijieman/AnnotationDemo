package com.foo.useinject.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/3/15 16:38
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {

    Class<?> listenerType();

    String listenerSetter();

    String methodName();
}
