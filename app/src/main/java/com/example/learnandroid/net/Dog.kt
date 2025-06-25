package com.example.learnandroid.net

import android.util.Log

/**
 * @author zhuhao zhuhao084@gmail.com
 **/
class Dog : Animal() {
    override fun bark() {
        super.bark()
        Log.i("test", "")
    }
}