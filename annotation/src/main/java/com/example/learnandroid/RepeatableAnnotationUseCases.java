package com.example.learnandroid;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Repeatable注解的实际应用场景示例
 */
public class RepeatableAnnotationUseCases {

    //==== 场景1：API请求路径映射 ====
    
    // 路径容器注解
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface RequestMappings {
        RequestMapping[] value();
    }
    
    // 可重复的路径注解
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(RequestMappings.class)
    public @interface RequestMapping {
        String path();
        String method() default "GET";
    }
    
    //==== 场景2：多角色权限控制 ====
    
    // 角色容器注解
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface RolePermissions {
        RolePermission[] value();
    }
    
    // 可重复的角色权限注解
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(RolePermissions.class)
    public @interface RolePermission {
        String role();
        String action() default "read";
    }
    
    // 模拟控制器类
    public static class UserController {
        
        // 多个HTTP方法和路径映射到同一个方法
        @RequestMapping(path = "/user", method = "GET")
        @RequestMapping(path = "/users/current", method = "GET")
        @RequestMapping(path = "/api/user-info", method = "GET")
        // 多角色权限控制
        @RolePermission(role = "ADMIN", action = "read")
        @RolePermission(role = "USER", action = "read")
        public void getCurrentUser() {
            // 方法实现
        }
        
        @RequestMapping(path = "/user/{id}", method = "PUT")
        @RequestMapping(path = "/api/users/{id}", method = "PUT")
        @RolePermission(role = "ADMIN", action = "write")
        @RolePermission(role = "MANAGER", action = "write")
        public void updateUser() {
            // 方法实现
        }
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("===== @Repeatable注解的实际应用场景 =====");
        
        // 获取getCurrentUser方法的映射
        Method getCurrentUserMethod = UserController.class.getMethod("getCurrentUser");
        
        System.out.println("==== API路径映射示例 ====");
        RequestMapping[] mappings = getCurrentUserMethod.getAnnotationsByType(RequestMapping.class);
        for (RequestMapping mapping : mappings) {
            System.out.println("路径: " + mapping.path() + ", HTTP方法: " + mapping.method());
        }
        
        System.out.println("\n==== 角色权限控制示例 ====");
        RolePermission[] permissions = getCurrentUserMethod.getAnnotationsByType(RolePermission.class);
        for (RolePermission permission : permissions) {
            System.out.println("角色: " + permission.role() + ", 操作权限: " + permission.action());
        }
        
        System.out.println("\n==== 方法上所有注解 ====");
        Annotation[] annotations = getCurrentUserMethod.getAnnotations();
        System.out.println("注解列表: " + Arrays.toString(annotations));
        System.out.println("注解数量: " + annotations.length);
        
        System.out.println("\n==== 从updateUser方法获取路径映射 ====");
        Method updateUserMethod = UserController.class.getMethod("updateUser");
        RequestMapping[] updateMappings = updateUserMethod.getAnnotationsByType(RequestMapping.class);
        for (RequestMapping mapping : updateMappings) {
            System.out.println("路径: " + mapping.path() + ", HTTP方法: " + mapping.method());
        }
    }
} 