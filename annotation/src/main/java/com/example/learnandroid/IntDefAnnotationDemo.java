package com.example.learnandroid;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 学习使用 IntDef 注解
 *
 * @author zhuhao
 * @date 02:08
 **/
public class IntDefAnnotationDemo {
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.PARAMETER})
    @IntDef(value = {IntDefAnnotationDemo.GOLD_ID, IntDefAnnotationDemo.SILVER_ID})
    public @interface QuoteType {

    }

    public static final int GOLD_ID = 12;
    public static final int SILVER_ID = 11;

    public static void main(String[] args) {
        queryData(IntDefAnnotationDemo.GOLD_ID);
        queryData(IntDefAnnotationDemo.SILVER_ID);
    }

    public static void queryData(@QuoteType int quoteType) {
        System.out.println("查询品种ID:" + quoteType + "的数据");
    }
}
