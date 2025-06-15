package com.example.learnandroid

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * 图片裁切拉伸演示
 * 展示所有不同的裁切拉伸类型 (ScaleType)
 */
class ImageScaleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_scale)
        
        // 图片URL
        val imageUrl = "https://sthumb.gkoudai.com/qp/6day_6064a7ccf2052c50a9d53edf3a9f4172.png"
        
        // 加载原始图片
        val originalImageView = findViewById<ImageView>(R.id.originalImageView)
        Glide.with(this)
             .load(imageUrl)
             .into(originalImageView)
             
        // 1. 加载 centerCrop 裁切图片
        val centerCropImageView = findViewById<ImageView>(R.id.centerCropImageView)
        Glide.with(this)
             .load(imageUrl)
             .apply(RequestOptions().centerCrop())
             .into(centerCropImageView)
             
        // 2. 加载 fitCenter 裁切图片
        val fitCenterImageView = findViewById<ImageView>(R.id.fitCenterImageView)
        Glide.with(this)
             .load(imageUrl)
             .apply(RequestOptions().fitCenter())
             .into(fitCenterImageView)
             
        // 3. 加载 fitXY 裁切图片 (使用普通ImageView的scaleType，因为Glide没有直接对应的方法)
        val fitXYImageView = findViewById<ImageView>(R.id.fitXYImageView)
        Glide.with(this)
             .load(imageUrl)
             .into(fitXYImageView)
        // fitXY 已在XML中设置 android:scaleType="fitXY"
             
        // 4. 加载 center 裁切图片
        val centerImageView = findViewById<ImageView>(R.id.centerImageView)
        Glide.with(this)
             .load(imageUrl)
             .apply(RequestOptions().dontTransform()) // 使用原始大小
             .into(centerImageView)
        // center 已在XML中设置 android:scaleType="center"
             
        // 5. 加载 centerInside 裁切图片
        val centerInsideImageView = findViewById<ImageView>(R.id.centerInsideImageView)
        Glide.with(this)
             .load(imageUrl)
             .apply(RequestOptions().centerInside())
             .into(centerInsideImageView)
             
        // 6. 加载 fitStart 裁切图片
        val fitStartImageView = findViewById<ImageView>(R.id.fitStartImageView)
        Glide.with(this)
             .load(imageUrl)
             .into(fitStartImageView)
        // fitStart 已在XML中设置 android:scaleType="fitStart"
             
        // 7. 加载 fitEnd 裁切图片
        val fitEndImageView = findViewById<ImageView>(R.id.fitEndImageView)
        Glide.with(this)
             .load(imageUrl)
             .into(fitEndImageView)
        // fitEnd 已在XML中设置 android:scaleType="fitEnd"
             
        // 8. 加载 matrix 裁切图片
        val matrixImageView = findViewById<ImageView>(R.id.matrixImageView)
        Glide.with(this)
             .load(imageUrl)
             .into(matrixImageView)
        // matrix 已在XML中设置 android:scaleType="matrix"
    }
} 