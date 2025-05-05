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
import androidx.compose.runtime.MutableState
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
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.learnandroid.ApplicationLifecycleObserver
import com.example.learnandroid.ui.components.FakeStatusBar
import com.example.learnandroid.ui.components.TitleBar
import com.example.learnandroid.ui.theme.LearnAndroidTheme
import com.example.learnandroid.utils.BarUtils
import kotlinx.coroutines.CoroutineScope
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

        // 初始化ViewModel
        demoViewModel = ViewModelProvider(this)[LifecycleDemoViewModel::class.java]

        // 添加 Application 生命周期观察者
        ProcessLifecycleOwner.get().lifecycle.addObserver(
            ApplicationLifecycleObserver { event, tag ->
                demoViewModel.addEvent(event, tag)
            }
        )

        // 添加 Activity 生命周期观察者
        lifecycle.addObserver(lifecycleObserver)

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

    // 使用LiveData存储生命周期事件日志，修改为存储EventLog对象
    private val _lifecycleEvents = MutableLiveData<List<EventLog>>(listOf())
    val lifecycleEvents: LiveData<List<EventLog>> = _lifecycleEvents

    // 添加生命周期事件到日志
    fun addEvent(event: String, tag: String = "") {
        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .format(Date())
        val eventWithTimestamp = "[$currentTime] $event"
        val eventTag = if (tag.isNotEmpty()) tag else extractTagFromEvent(event)
        val eventLog = EventLog(eventWithTimestamp, eventTag)
        val currentList = _lifecycleEvents.value ?: listOf()
        _lifecycleEvents.value = currentList + eventLog
    }

    // 从事件文本中提取标签
    private fun extractTagFromEvent(event: String): String {
        return when {
            event.contains("App Lifecycle") -> "APP"
            event.contains("Lifecycle Event") -> "ACTIVITY"
            event.contains("ViewModel") -> "VIEWMODEL"
            event.contains("模拟耗时操作") -> "OPERATION"
            else -> "OTHER"
        }
    }

    init {
        Log.d(TAG, "ViewModel initialized")
        addEvent("ViewModel initialized", "VIEWMODEL")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}

// 事件日志数据类
data class EventLog(
    val message: String,
    val tag: String
)

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
            viewModel.addEvent("Lifecycle Event: ${event.name}", "ACTIVITY")
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

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
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
            }

            item {
                LifecycleOperateCard(scope, viewModel, showDialog)
            }

            item {
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

                        Column {
                            lifecycleEvents.forEach { event ->
                                val color = getColorForTag(event.tag)
                                Text(
                                    text = "• ${event.message}",
                                    fontSize = 12.sp,
                                    color = color,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // 当列表数据更新时，自动滚动到底部
    LaunchedEffect(lifecycleEvents) {
        if (lifecycleEvents.isNotEmpty()) {
            lazyListState.animateScrollToItem(lifecycleEvents.size - 1)
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
}

@Composable
private fun LifecycleOperateCard(
    scope: CoroutineScope,
    viewModel: LifecycleDemoViewModel,
    showDialog: MutableState<Boolean>
) {
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
}

@Composable
private fun getColorForTag(tag: String): androidx.compose.ui.graphics.Color {
    return when (tag) {
        "APP" -> androidx.compose.ui.graphics.Color(0xFFE91E63) // Pink
        "ACTIVITY" -> androidx.compose.ui.graphics.Color(0xFF2196F3) // Blue
        "VIEWMODEL" -> androidx.compose.ui.graphics.Color(0xFF4CAF50) // Green
        "OPERATION" -> androidx.compose.ui.graphics.Color(0xFFFF9800) // Orange
        else -> androidx.compose.ui.graphics.Color.Gray
    }
}

@Composable
fun LiveData<List<EventLog>>.collectAsState(
    initial: List<EventLog>
): State<List<EventLog>> {
    val state = remember { mutableStateOf(initial) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(this, lifecycleOwner) {
        val observer = Observer<List<EventLog>> { value ->
            state.value = value
        }

        observe(lifecycleOwner, observer)
        onDispose {
            removeObserver(observer)
        }
    }

    return state
}