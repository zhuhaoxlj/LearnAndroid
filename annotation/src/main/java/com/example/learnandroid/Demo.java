package com.example.learnandroid;

/**
 * @author zhuhao zhuhao084@gmail.com
 **/
public class Demo {
    interface Test {
        void say();
    }

    static class Animal implements Test {
        String name;
        int age;

        @Override
        public void say() {

        }

        public void eat() {

        }
    }

    static class Dog extends Animal{

    }

    public static void main(String[] args) throws NoSuchMethodException {
        int a = 0, b = 0, c = 0;
        a = b = 3;
        Animal animal = new Animal();
        Dog dog = new Dog();
        System.out.println(animal.getClass().getMethod("say").getDeclaringClass());
        System.out.println(dog.getClass().getMethod("toString").getDeclaringClass());
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }

    public <T> T create(Class<T> service) throws InstantiationException, IllegalAccessException {
        Class<?>[] classes = new Class<?>[]{(service)};

        classes.clone();
        return (T) service.newInstance();
    }
}
