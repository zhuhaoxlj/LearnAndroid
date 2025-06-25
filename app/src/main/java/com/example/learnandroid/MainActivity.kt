package com.example.learnandroid

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import com.example.learnandroid.bean.Repo
import com.example.learnandroid.net.GithubService
import com.example.learnandroid.ui.components.CustomAppBottomBar
import com.example.learnandroid.ui.screens.CommunityScreen
import com.example.learnandroid.ui.screens.LearningScreen
import com.example.learnandroid.ui.screens.ProfileScreen
import com.example.learnandroid.ui.theme.LearnAndroidTheme
import com.example.learnandroid.utils.BarUtils
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Android 学习宝典 程序入口
 */
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "savedInstanceState: ${Gson().toJson(savedInstanceState)}")
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
                    Column(modifier = Modifier.fillMaxSize()) {
                        // 添加演示按钮布局
                        AndroidView(
                            factory = { context ->
                                LinearLayout(context).apply {
                                    orientation = LinearLayout.VERTICAL

                                    // 内存泄漏演示按钮
                                    addView(Button(context).apply {
                                        text = "打开内存泄漏演示"
                                        setOnClickListener {
                                            LeakDemoActivity.start(context)
                                        }
                                    })

                                    // 图片裁切拉伸演示按钮
                                    addView(Button(context).apply {
                                        text = "图片裁切拉伸演示"
                                        setOnClickListener {
                                            val intent =
                                                Intent(context, ImageScaleActivity::class.java)
                                            context.startActivity(intent)
                                        }
                                    })
                                }
                            }
                        )

                        // 主界面
                        MainScreen(Modifier.weight(1f))
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.github.com/").build()
        val service = retrofit.create<GithubService>(GithubService::class.java)
        val repos = service.listRepos("zhuhaoxlj")
        repos.enqueue(object : Callback<List<Repo>> {
            override fun onResponse(
                call: Call<List<Repo>>,
                response: Response<List<Repo>>
            ) {
                println("Response:${response.body()?.getOrNull(0)}")
            }

            override fun onFailure(
                call: Call<List<Repo>>,
                t: Throwable
            ) {
                println("Error: ${t.message}")
            }
        })
        Log.i(TAG, "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
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
fun MainScreen(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(initialPage = 0) { 3 }
    Column(modifier = modifier.fillMaxSize()) {
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

