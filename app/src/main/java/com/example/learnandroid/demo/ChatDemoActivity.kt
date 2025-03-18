package com.example.learnandroid.demo

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.learnandroid.ui.components.CustomAppBottomBar
import com.example.learnandroid.ui.components.FakeStatusBar
import com.example.learnandroid.ui.screens.ChatList
import com.example.learnandroid.ui.theme.LearnAndroidTheme
import com.example.learnandroid.utils.BarUtils
import com.example.learnandroid.vm.LearnAndroidVM
import kotlinx.coroutines.launch

class ChatDemoActivity : AppCompatActivity() {
    private val viewModel: LearnAndroidVM by viewModels()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ChatDemoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT)
        BarUtils.setStatusBarLightMode(this, true)
        initComposeView()
    }

    private fun initComposeView() {
        setContent {
            LearnAndroidTheme {
                ChatDemoPage()
            }
        }
    }
}

@Composable
private fun ChatDemoPage() {
    Column {
        val viewModel: LearnAndroidVM = androidx.lifecycle.viewmodel.compose.viewModel()
        val pagerState = rememberPagerState { 4 }
        FakeStatusBar(LearnAndroidTheme.themeColors.listItem)
        HorizontalPager(
            state = pagerState,
            Modifier.weight(1f)
        ) {
            when (it) {
                0 -> ChatList(viewModel.chats)
                else -> {}
            }
        }
        val scope = rememberCoroutineScope()
        CustomAppBottomBar(pagerState.currentPage) { page ->
            scope.launch {
                pagerState.animateScrollToPage(page)
            }
        }
    }
}

@Preview
@Composable
fun ChatDemoPagePreview() {
    LearnAndroidTheme {
        ChatDemoPage()
    }
} 