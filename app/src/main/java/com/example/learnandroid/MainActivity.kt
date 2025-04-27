package com.example.learnandroid

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.learnandroid.ui.components.CustomAppBottomBar
import com.example.learnandroid.ui.screens.CommunityScreen
import com.example.learnandroid.ui.screens.LearningScreen
import com.example.learnandroid.ui.screens.ProfileScreen
import com.example.learnandroid.ui.theme.LearnAndroidTheme
import com.example.learnandroid.utils.BarUtils
import com.google.gson.Gson
import kotlinx.coroutines.launch

/**
 * Android 学习宝典 程序入口
 */
class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, Gson().toJson(savedInstanceState))
        Log.i(TAG, "onCreate")

        // 设置透明状态栏，实现沉浸式效果
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT)
        BarUtils.setStatusBarLightMode(this, true)

        // 告诉系统我们的内容要延伸到系统栏区域
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 确保状态栏是透明的
        window.statusBarColor = Color.TRANSPARENT

        setContent {
            LearnAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }
}

@Composable
fun MainScreen() {
    val pagerState = rememberPagerState(initialPage = 0) { 3 }
    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> LearningScreen()
                1 -> CommunityScreen()
                2 -> ProfileScreen()
            }
        }

        // Bottom navigation bar
        val scope = rememberCoroutineScope()
        CustomAppBottomBar(pagerState.currentPage) { page ->
            scope.launch {
                pagerState.animateScrollToPage(page)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    LearnAndroidTheme {
        MainScreen()
    }
}

