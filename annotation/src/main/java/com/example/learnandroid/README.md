# Java `@Repeatable` 注解详解

## 什么是 `@Repeatable` 注解？

`@Repeatable` 是Java 8引入的一个元注解（即注解的注解），它允许在同一个声明上（类、方法、字段等）多次使用相同类型的注解。在Java 8之前，如果想要在同一位置重复使用同一类型的注解，必须使用一个容器注解来封装，这种写法比较繁琐。

## `@Repeatable` 注解的作用

1. **简化代码**：可以直接重复使用注解，而不需要通过容器注解包装
2. **提高可读性**：代码更加清晰，意图更明确
3. **减少冗余**：去掉了额外的容器注解嵌套
4. **更符合直觉**：使用方式更自然，更符合开发者的思维方式

## 使用 `@Repeatable` 的步骤

要使用 `@Repeatable` 注解，需要遵循以下三个步骤：

### 1. 定义容器注解

首先要定义一个容器注解，用于存放重复的注解实例：

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Schedules {
    Schedule[] value();  // 必须有一个value方法返回被重复注解的数组
}
```

### 2. 使用 `@Repeatable` 标记可重复的注解

为要重复使用的注解添加 `@Repeatable` 注解，并指向容器注解：

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(Schedules.class)  // 指向容器注解
public @interface Schedule {
    String dayOfMonth();
    String dayOfWeek();
    int hour() default 12;
}
```

### 3. 重复使用注解

现在可以直接重复使用注解：

```java
@Schedule(dayOfMonth = "1", dayOfWeek = "Mon", hour = 8)
@Schedule(dayOfMonth = "15", dayOfWeek = "Fri", hour = 14)
public class TaskScheduler {
    // 类定义
}
```

## 获取重复注解的方法

有两种方式可以获取被 `@Repeatable` 标记的注解：

### 1. 使用 `getAnnotationsByType()` 方法（推荐）

Java 8专门为重复注解提供的方法，更直观：

```java
Schedule[] schedules = TaskScheduler.class.getAnnotationsByType(Schedule.class);
for (Schedule schedule : schedules) {
    System.out.println(schedule.dayOfMonth() + " " + schedule.dayOfWeek());
}
```

### 2. 通过容器注解获取（传统方式）

```java
Schedules schedulesContainer = TaskScheduler.class.getAnnotation(Schedules.class);
if (schedulesContainer != null) {
    for (Schedule schedule : schedulesContainer.value()) {
        System.out.println(schedule.dayOfMonth() + " " + schedule.dayOfWeek());
    }
}
```

## 实际应用场景

`@Repeatable` 注解在许多场景下非常有用，例如：

1. **API路径映射**：同一方法可以处理多个URL路径
2. **安全角色控制**：多个角色可以访问同一资源
3. **事件处理**：一个组件可以处理多种类型的事件
4. **异常处理**：一个处理器可以处理多种异常类型
5. **校验规则**：一个字段可以应用多种验证规则

## 注意事项

1. 虽然代码中直接重复使用注解，但在字节码层面，Java编译器仍然将它们封装在容器注解中
2. 要确保容器注解和可重复注解的 `@Retention` 和 `@Target` 保持一致
3. 容器注解必须有一个名为 `value()` 的方法，返回被重复注解的数组

## 示例代码

本项目包含以下演示`@Repeatable`的示例：

1. `RepeatableAnnotationDemo.java` - 基本的任务调度示例
2. `RepeatableAnnotationExample.java` - 课程标签示例
3. `OldVsNewStyle.java` - Java 8前后写法对比
4. `RepeatableAnnotationUseCases.java` - 实际应用场景示例

## 和传统方式的比较

### Java 8前的写法

```java
@OldFilters({
    @OldFilter("filter1"),
    @OldFilter("filter2"),
    @OldFilter("filter3")
})
public class OldClass { }
```

### Java 8后的写法

```java
@NewFilter("filter1")
@NewFilter("filter2")
@NewFilter("filter3")
public class NewClass { } 