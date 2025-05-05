package com.example.learnandroid;

import java.lang.annotation.*;
import java.util.Arrays;

/**
 * @Repeatable注解示例
 * 
 * @Repeatable注解允许在同一个声明上多次使用相同的注解类型。
 * 在Java 8之前，如果想在同一个地方使用同一个注解多次，需要使用容器注解，代码比较繁琐。
 * Java 8引入@Repeatable后，可以直接重复使用注解，编译器会自动处理。
 */
public class RepeatableAnnotationExample {
    
    // 1. 定义容器注解类型
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Topics {
        Topic[] value();
    }
    
    // 2. 定义可重复的注解，通过@Repeatable指向容器注解
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Repeatable(Topics.class)
    public @interface Topic {
        String name();
        int priority() default 1;
    }
    
    // 3. 使用重复注解标记的类
    @Topic(name = "Java基础", priority = 3)
    @Topic(name = "注解", priority = 2)
    @Topic(name = "Java 8特性", priority = 1)
    public static class JavaCourse {
        private String title;
        
        public JavaCourse(String title) {
            this.title = title;
        }
        
        public String getTitle() {
            return title;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("===== @Repeatable注解示例 =====");
        
        // 方法1：使用getAnnotationsByType获取所有重复注解（推荐方式）
        Topic[] topics = JavaCourse.class.getAnnotationsByType(Topic.class);
        System.out.println("课程主题数量: " + topics.length);
        for (Topic topic : topics) {
            System.out.println("主题: " + topic.name() + ", 优先级: " + topic.priority());
        }
        
        System.out.println("\n===== 容器注解方式 =====");
        
        // 方法2：通过容器注解间接获取（传统方式）
        Topics topicsContainer = JavaCourse.class.getAnnotation(Topics.class);
        if (topicsContainer != null) {
            for (Topic topic : topicsContainer.value()) {
                System.out.println("主题: " + topic.name() + ", 优先级: " + topic.priority());
            }
        }
        
        // 查看类上的所有注解
        System.out.println("\n===== 所有注解 =====");
        Annotation[] annotations = JavaCourse.class.getAnnotations();
        System.out.println("注解列表: " + Arrays.toString(annotations));
        // 注意：虽然代码中写了三个@Topic注解，但实际上Java会把它们合并到一个@Topics容器注解中
    }
} 