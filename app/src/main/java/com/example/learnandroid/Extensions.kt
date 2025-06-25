package com.example.learnandroid

import android.content.res.Resources

/**
 * @author zhuhao
 * @date  00:50
 **/
val Float.px
    get() = this.toFloat() * Resources.getSystem().displayMetrics.density