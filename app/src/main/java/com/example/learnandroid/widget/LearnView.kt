package com.example.learnandroid.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathDashPathEffect
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.learnandroid.utils.dp

val DASH_WIDTH = 2f.dp
val DASH_HEIGHT = 10f.dp

/**
 * @author zhuhao zhuhao084@gmail.com
 **/
class LearnView(context: Context?, attr: AttributeSet) : View(context, attr) {
    private val path = Path()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dash = Path()
    private var dashPathEffect: PathDashPathEffect? = null
    private var pathMeasure: PathMeasure? = null

    init {
        paint.strokeWidth = 3f.dp
        paint.style = Paint.Style.STROKE
        dash.addRect(0f, 0f, DASH_WIDTH, DASH_HEIGHT, Path.Direction.CCW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        Log.i("onSizeChanged", "trigger")
        path.reset()

        // 添加圆弧
        path.addArc(
            width / 2 - 150f.dp,
            height / 2 - 150f.dp,
            width / 2 + 150f.dp,
            height / 2 + 150f.dp,
            135f, 270f,
        )

        pathMeasure = PathMeasure(path, false)
        dashPathEffect = PathDashPathEffect(
            dash,
            (pathMeasure!!.length - DASH_WIDTH) / 20f,
            0f,
            PathDashPathEffect.Style.ROTATE
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 画圆弧
        canvas.drawPath(path, paint)

        paint.pathEffect = dashPathEffect

        // 画指针
        canvas.drawPath(path, paint)
        paint.pathEffect = null
    }
}