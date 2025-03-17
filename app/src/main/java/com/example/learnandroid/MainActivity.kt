package com.example.learnandroid

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learnandroid.databinding.ActivityMainBinding
import com.example.learnandroid.problem.GlideProblemActivity
import com.example.learnandroid.ui.components.CustomAppBottomBar
import com.example.learnandroid.ui.screens.ChatList
import com.example.learnandroid.ui.theme.LearnAndroidTheme
import com.example.learnandroid.vm.LearnAndroidVM
import com.example.learnandroid.vm.MainVM
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val wishViewModel: MainVM by viewModels()
    private val viewModel: LearnAndroidVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initTraditionalView()
        initComposeView()
    }

    private fun initComposeView() {
        setContent {
            LearnAndroidTheme {
                HomePage()
            }
        }
    }

    private fun initTraditionalView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rbGlideProblem.setOnClickListener {
            GlideProblemActivity.start(this)
        }
        binding.rbCustomTouch.setOnClickListener {
            ViewTouchActivity.start(this)
        }
        binding.cvView.setContent {
            WishListScreenPreview(wishViewModel.wishList)  // 传入 ViewModel 的数据
        }
    }
}

@Composable
private fun HomePage() {
    Column {
        val viewModel: LearnAndroidVM = viewModel()
        val pagerState = rememberPagerState { 4 } // 初始页面数为4
        HorizontalPager(
            state = pagerState,  // 必须传递 state
            Modifier.weight(1f)
        ) {
            when (it) {
                0 -> ChatList(viewModel.chats)
                1 -> Box(Modifier.fillMaxSize())
                2 -> Box(Modifier.fillMaxSize())
            }
        }
        val scope = rememberCoroutineScope() // 创建 CoroutineScope
        CustomAppBottomBar(viewModel.selectedTab) { page ->
            // 点击页签后，在协程里翻页
            scope.launch {
                pagerState.animateScrollToPage(page)
            }
        }
    }
}

@Preview
@Composable
fun HomePagePreview() {
    LearnAndroidTheme {
        HomePage()
    }
}

@Preview
@Composable
fun WishListScreenPreview(wishList: List<String> = listOf("apple", "banana", "orange")) {
    WishListScreen(wishList)
}

@Composable
fun WishListScreen(wishList: List<String>) {
    LazyColumn {
        items(wishList.size) { index ->
            val wish = wishList[index]
            Column {
                Text(wish)
            }
        }
    }
}

