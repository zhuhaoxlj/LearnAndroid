package com.example.learnandroid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited // 如果父类使用了这个注解，那么继承它的子类也有这个注解
public @interface ParentAnnotation {

    String key();

    String value();
}

