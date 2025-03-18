package com.example.learnandroid.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.learnandroid.ViewTouchActivity
import com.example.learnandroid.demo.ChatDemoActivity
import com.example.learnandroid.problem.GlideProblemActivity
import com.example.learnandroid.ui.components.FakeStatusBar
import com.example.learnandroid.ui.components.TitleBar
import com.example.learnandroid.ui.theme.LearnAndroidTheme

data class LearningItem(
    val title: String,
    val description: String,
    val onClick: () -> Unit
)

@Composable
fun LearningScreen() {
    val context = LocalContext.current

    // Demo items
    val learningItems = listOf(
        LearningItem(
            "聊天界面 Demo",
            "使用 Jetpack Compose 实现的聊天界面示例",
            { ChatDemoActivity.start(context) }
        ),
        LearningItem(
            "Glide 问题演示",
            "展示和解决 Glide 加载图片时的常见问题",
            { GlideProblemActivity.start(context) }
        ),
        LearningItem(
            "自定义触摸反馈",
            "Android 触摸事件处理示例",
            { ViewTouchActivity.start(context) }
        )
    )

    Column(modifier = Modifier.fillMaxSize()) {
        FakeStatusBar(color = Color(0xFFFFFFFF))

        // Title bar
        TitleBar(
            title = "学习",
            backgroundColor = LearnAndroidTheme.themeColors.listItem,
            titleColor = LearnAndroidTheme.themeColors.textPrimary
        )

        // Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Text(
                    text = "Android 学习宝典",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            items(learningItems) { item ->
                DemoCard(
                    title = item.title,
                    description = item.description,
                    onClick = item.onClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LearningScreenPreview() {
    LearnAndroidTheme {
        LearningScreen()
    }
} 