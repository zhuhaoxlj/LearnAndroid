package com.example.learnandroid;

import java.lang.annotation.*;

public class RepeatableAnnotationDemo {
    
    // 1. 定义容器注解
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Schedules {
        Schedule[] value();
    }
    
    // 2. 定义可重复的注解，使用@Repeatable指向容器注解
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Repeatable(Schedules.class)
    public @interface Schedule {
        String dayOfMonth() default "1";
        String dayOfWeek() default "Mon";
        int hour() default 12;
    }
    
    // 3. 使用重复注解的类
    @Schedule(dayOfMonth = "1", dayOfWeek = "Mon", hour = 8)
    @Schedule(dayOfMonth = "15", dayOfWeek = "Fri", hour = 14)
    public static class TaskScheduler {
        public void showSchedules() {
            // 获取类上的所有Schedule注解
            Schedule[] schedules = TaskScheduler.class.getAnnotationsByType(Schedule.class);
            System.out.println("任务计划数量: " + schedules.length);
            
            for (Schedule schedule : schedules) {
                System.out.println("计划时间: 每月" + schedule.dayOfMonth() + 
                                  "日, 每周" + schedule.dayOfWeek() + 
                                  ", " + schedule.hour() + "点");
            }
            
            // 获取容器注解
            Schedules schedulesContainer = TaskScheduler.class.getAnnotation(Schedules.class);
            if (schedulesContainer != null) {
                System.out.println("\n通过容器注解获取:");
                for (Schedule schedule : schedulesContainer.value()) {
                    System.out.println("计划时间: 每月" + schedule.dayOfMonth() + 
                                      "日, 每周" + schedule.dayOfWeek() + 
                                      ", " + schedule.hour() + "点");
                }
            }
        }
    }
    
    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.showSchedules();
    }
} 