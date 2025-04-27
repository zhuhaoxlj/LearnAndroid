package com.example.learnandroid

import android.app.Application

/**
 * @author zhuhao
 * @date  17:07
 **/
class AppApplication : Application() {
    var isCold = true
    override fun onCreate() {
        super.onCreate()
        if (isCold) {
            isCold = false
        }
    }
}