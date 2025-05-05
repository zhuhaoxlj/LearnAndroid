package com.example.learnandroid;

import java.lang.annotation.*;

/**
 * 对比Java 8前后重复注解的使用差异
 */
public class OldVsNewStyle {

    //=== Java 8前的写法 ===
    
    // 1. 定义容器注解
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface OldFilters {
        OldFilter[] value();
    }
    
    // 2. 定义注解
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface OldFilter {
        String value();
    }
    
    // 3. 使用方式：必须使用容器注解
    @OldFilters({
        @OldFilter("filter1"),
        @OldFilter("filter2"),
        @OldFilter("filter3")
    })
    public static class OldClass {
        // 类定义
    }
    
    //=== Java 8后的写法 ===
    
    // 1. 定义容器注解
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface NewFilters {
        NewFilter[] value();
    }
    
    // 2. 定义注解并标记为@Repeatable
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Repeatable(NewFilters.class)
    public @interface NewFilter {
        String value();
    }
    
    // 3. 使用方式：可以直接重复使用注解
    @NewFilter("filter1")
    @NewFilter("filter2")
    @NewFilter("filter3")
    public static class NewClass {
        // 类定义
    }
    
    public static void main(String[] args) {
        System.out.println("===== Java 8之前的重复注解 =====");
        OldFilters oldFilters = OldClass.class.getAnnotation(OldFilters.class);
        if (oldFilters != null) {
            for (OldFilter filter : oldFilters.value()) {
                System.out.println("旧风格过滤器: " + filter.value());
            }
        }
        
        System.out.println("\n===== Java 8的@Repeatable注解 =====");
        
        // 方式1：通过容器注解获取
        NewFilters newFilters = NewClass.class.getAnnotation(NewFilters.class);
        if (newFilters != null) {
            System.out.println("通过容器注解获取:");
            for (NewFilter filter : newFilters.value()) {
                System.out.println("新风格过滤器: " + filter.value());
            }
        }
        
        // 方式2：直接用getAnnotationsByType获取所有重复注解（Java 8新增方法）
        System.out.println("\n通过getAnnotationsByType获取（Java 8新增）:");
        NewFilter[] filters = NewClass.class.getAnnotationsByType(NewFilter.class);
        for (NewFilter filter : filters) {
            System.out.println("新风格过滤器: " + filter.value());
        }
        
        // 说明：虽然代码写法上可以重复使用注解，但Java编译器在内部仍然使用容器注解来存储它们
    }
} 