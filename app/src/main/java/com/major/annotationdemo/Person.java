package com.major.annotationdemo;


import com.major.pd.annotation.Seriable;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/3/19 10:57
 */
public class Person {

    @Seriable
    String name;

    @Seriable
    int age;
}
