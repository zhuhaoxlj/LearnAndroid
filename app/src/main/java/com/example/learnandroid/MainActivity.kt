package com.example.learnandroid

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.learnandroid.demo.ChatDemoActivity
import com.example.learnandroid.problem.GlideProblemActivity
import com.example.learnandroid.ui.theme.LearnAndroidTheme

/**
 * Android 学习宝典 程序入口
 */
class MainActivity : AppCompatActivity() {
    private lateinit var lifecycleObserver: MyLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationLifecycleObserver())

        // 创建并添加 LifecycleObserver
        lifecycleObserver = MyLifecycleObserver()
        lifecycle.addObserver(lifecycleObserver)

        setContent {
            LearnAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AndroidLearningGuide()
                }
            }
        }
    }
}

@Composable
fun AndroidLearningGuide() {
    val context = LocalContext.current

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

        // Demo 列表
        item {
            DemoCard(
                title = "聊天界面 Demo",
                description = "使用 Jetpack Compose 实现的聊天界面示例",
                onClick = { ChatDemoActivity.start(context) }
            )
        }

        item {
            DemoCard(
                title = "Glide 问题演示",
                description = "展示和解决 Glide 加载图片时的常见问题",
                onClick = { GlideProblemActivity.start(context) }
            )
        }

        item {
            DemoCard(
                title = "自定义触摸反馈",
                description = "Android 触摸事件处理示例",
                onClick = { ViewTouchActivity.start(context) }
            )
        }
    }
}

@Composable
fun DemoCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AndroidLearningGuidePreview() {
    LearnAndroidTheme {
        AndroidLearningGuide()
    }
}

