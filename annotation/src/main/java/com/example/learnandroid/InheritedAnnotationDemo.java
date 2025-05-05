package com.example.learnandroid;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * 学习注解 @Inherited
 */
public class InheritedAnnotationDemo extends Parent {
    public static void main(String[] args) {
        Annotation[] annotations = InheritedAnnotationDemo.class.getAnnotations();

        // 父类使用了含有 @Inherited 注解，子类也会有这个注解
        System.out.println(Arrays.toString(annotations));
    }
}