package com.example.learnandroid.ui.screens

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.learnandroid.ui.components.FakeStatusBar
import com.example.learnandroid.ui.components.TitleBar
import com.example.learnandroid.ui.theme.LearnAndroidTheme
import com.example.learnandroid.utils.BarUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LifecycleDemoActivity : AppCompatActivity() {

    private val TAG = "LifecycleDemoActivity"
    private lateinit var demoViewModel: LifecycleDemoViewModel
    private val lifecycleObserver = DemoLifecycleObserver()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LifecycleDemoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStatusBar()

        Log.d(TAG, "onCreate called")

        // 添加生命周期观察者
        lifecycle.addObserver(lifecycleObserver)

        // 初始化ViewModel
        demoViewModel = ViewModelProvider(this)[LifecycleDemoViewModel::class.java]

        setContent {
            LearnAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LifecycleDemoScreen(demoViewModel, lifecycle, this@LifecycleDemoActivity)
                }
            }
        }
    }

    private fun initStatusBar() {
        // 设置透明状态栏，实现沉浸式效果
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT)
        BarUtils.setStatusBarLightMode(this, true)

        // 告诉系统我们的内容要延伸到系统栏区域
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 确保状态栏是透明的
        window.statusBarColor = Color.TRANSPARENT
    }

    override fun onDestroy() {
        super.onDestroy()
        // 移除生命周期观察者
        lifecycle.removeObserver(lifecycleObserver)
    }
}

// 生命周期观察者示例
class DemoLifecycleObserver : LifecycleEventObserver {
    private val TAG = "DemoLifecycleObserver"

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d(TAG, "Lifecycle Event: $event")
    }
}

// ViewModel示例，演示ViewModel的生命周期
class LifecycleDemoViewModel : ViewModel() {
    private val TAG = "LifecycleDemoViewModel"

    // 使用LiveData存储生命周期事件日志
    private val _lifecycleEvents = MutableLiveData<List<String>>(listOf())
    val lifecycleEvents: LiveData<List<String>> = _lifecycleEvents

    // 添加生命周期事件到日志
    fun addEvent(event: String) {
        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .format(Date())
        val eventWithTimestamp = "[$currentTime] $event"
        val currentList = _lifecycleEvents.value ?: listOf()
        _lifecycleEvents.value = currentList + eventWithTimestamp
    }

    init {
        Log.d(TAG, "ViewModel initialized")
        addEvent("ViewModel initialized")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}

@Preview(showBackground = true)
@Composable
fun LifecycleDemoScreenPreview() {
    val viewModel = LifecycleDemoViewModel()
    LearnAndroidTheme {
        LifecycleDemoScreen(
            viewModel = viewModel,
            lifecycle = LocalLifecycleOwner.current.lifecycle,
            activity = AppCompatActivity()
        )
    }
}

@Composable
fun LifecycleDemoScreen(viewModel: LifecycleDemoViewModel, lifecycle: Lifecycle, activity: AppCompatActivity) {
    val lifecycleEvents by viewModel.lifecycleEvents.collectAsState(initial = listOf())
    val currentState = remember { mutableStateOf(lifecycle.currentState.name) }
    val showDialog = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    // 使用DisposableEffect监听生命周期变化
    DisposableEffect(key1 = lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            viewModel.addEvent("Lifecycle Event: ${event.name}")
            currentState.value = lifecycle.currentState.name
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LearnAndroidTheme.themeColors.background)
    ) {
        FakeStatusBar(LearnAndroidTheme.themeColors.background)

        TitleBar(
            title = "Jetpack Lifecycle 演示",
            onLeftClick = { activity.finish() },
            backgroundColor = LearnAndroidTheme.themeColors.background,
            titleColor = LearnAndroidTheme.themeColors.textPrimary
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "当前生命周期状态",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "State: ${currentState.value}")
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "生命周期操作",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.addEvent("模拟耗时操作开始")
                                delay(2000)
                                viewModel.addEvent("模拟耗时操作结束")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("模拟耗时操作")
                    }
                    Button(
                        onClick = {
                            // 点击按钮时，将状态变量设置为 true，显示对话框
                            showDialog.value = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("弹出 Dialog")
                    }
                }
            }
            // 当 showDialog 为 true 时，显示对话框
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        // 点击对话框外部时，关闭对话框
                        showDialog.value = false
                    },
                    title = { Text("对话框标题") },
                    text = { Text("这是一个 Dialog 示例") },
                    confirmButton = {
                        TextButton(onClick = {
                            // 确认按钮点击处理
                            showDialog.value = false
                        }) {
                            Text("确认")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            // 取消按钮点击处理
                            showDialog.value = false
                        }) {
                            Text("取消")
                        }
                    }
                )
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "生命周期事件日志",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(
                        state = lazyListState
                    ) {
                        items(lifecycleEvents.size) { index ->
                            Text(
                                text = "• ${lifecycleEvents[index]}",
                                fontSize = 12.sp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }

                    // 当列表数据更新时，自动滚动到底部
                    LaunchedEffect(lifecycleEvents) {
                        if (lifecycleEvents.isNotEmpty()) {
                            lazyListState.animateScrollToItem(lifecycleEvents.size - 1)
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun LiveData<List<String>>.collectAsState(
    initial: List<String>
): State<List<String>> {
    val state = remember { mutableStateOf(initial) }
    val lifecycleOwner = LocalLifecycleOwner.current


    DisposableEffect(this, lifecycleOwner) {
        val observer = Observer<List<String>> { value ->
            state.value = value
        }

        observe(lifecycleOwner, observer)
        onDispose {
            removeObserver(observer)
        }
    }

    return state
}