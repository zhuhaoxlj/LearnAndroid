package com.example.learnandroid

import android.content.res.Resources

/**
 * @author zhuhao
 * @date  00:50
 **/
val Float.dp
    get() = this.toFloat() * Resources.getSystem().displayMetrics.density