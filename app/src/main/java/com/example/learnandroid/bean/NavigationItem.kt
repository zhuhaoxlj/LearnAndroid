package com.example.learnandroid.bean

import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 底部导航栏对象
 * @author zhuhao
 * @date  16:16
 **/
data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val iconDrawable: Drawable? = null,
    val msgNum: Int
)