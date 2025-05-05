package com.example.learnandroid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自己的绑定 view 注解
 *
 * @author zhuhao
 * @date 02:34
 **/
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface MyBindView {
}
