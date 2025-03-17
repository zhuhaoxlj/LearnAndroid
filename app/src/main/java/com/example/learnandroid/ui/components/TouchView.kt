package com.example.learnandroid.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.learnandroid.dp

/**
 * 触摸反馈
 *
 * @author zhuhao
 * @date  22:19
 **/
class TouchView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.actionMasked == MotionEvent.ACTION_UP) {
            performClick()
        }
        val a = 11f.dp
        return true
    }
}