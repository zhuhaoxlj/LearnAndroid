package com.example.learnandroid.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author zhuhao zhuhao084@gmail.com
 **/
val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )