package com.example.learnandroid.demo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
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
        val currentList = _lifecycleEvents.value ?: listOf()
        _lifecycleEvents.value = currentList + event
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

@Composable
fun LifecycleDemoScreen(viewModel: LifecycleDemoViewModel, lifecycle: Lifecycle, activity: AppCompatActivity) {
    val lifecycleEvents by viewModel.lifecycleEvents.collectAsState(initial = listOf())
    val currentState = remember { mutableStateOf(lifecycle.currentState.name) }
    val scope = rememberCoroutineScope()

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

    Column(modifier = Modifier.fillMaxSize()) {
        FakeStatusBar()

        TitleBar(
            title = "Jetpack Lifecycle 演示",
            onLeftClick = { activity.finish() },
            backgroundColor = LearnAndroidTheme.themeColors.background,
            titleColor = LearnAndroidTheme.themeColors.textPrimary
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
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
                    }
                }
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

                        lifecycleEvents.forEach { event ->
                            Text(
                                text = "• $event",
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
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