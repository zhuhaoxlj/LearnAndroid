package com.example.learnandroid.utils

import android.content.Context
import android.widget.Toast

/**
 * @author zhuhao
 * @date  22:15
 **/
class ToastUtils {
    companion object {
        fun toast(context: Context, text: String) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}