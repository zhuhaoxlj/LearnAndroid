package com.example.learnandroid.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnandroid.ui.theme.LearnAndroidTheme

/**
 * 标题栏
 *
 * @author zhuhao
 * @date  14:28
 **/

@Composable
fun TitleBar(
    leftIcon: ImageVector? = null,
    title: String,
    backgroundColor: Color,
    titleColor: Color,
    showDivider: Boolean = true,
    onLeftClick: (() -> Unit)? = null,
    onRightClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .height(55.dp)
    ) {
        if (leftIcon != null) {
            Icon(
                imageVector = leftIcon,
                tint = titleColor,
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(23.dp)
                    .fillMaxHeight()
                    .align(Alignment.CenterStart)
            )
        }
        Text(
            text = title,
            fontSize = 20.sp,
            color = titleColor,
            modifier = Modifier.align(Alignment.Center)
        )
        if (showDivider) {
            HorizontalDivider(thickness = 0.5.dp, modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TitleBarPreview() {
    TitleBar(
        title = "标题",
        backgroundColor = LearnAndroidTheme.themeColors.listItem,
        titleColor = LearnAndroidTheme.themeColors.textPrimary
    )
}