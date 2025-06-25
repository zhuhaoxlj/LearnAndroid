package com.example.learnandroid.ui.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learnandroid.ui.components.FakeStatusBar
import com.example.learnandroid.ui.components.TitleBar
import com.example.learnandroid.ui.theme.LearnAndroidTheme
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.IOException

class FlowDemoViewModel : ViewModel() {
    
    private val TAG = "FlowDemoViewModel"
    
    // 1. Basic Flow creation
    fun createSimpleFlow(): Flow<Int> = flow {
        for (i in 1..5) {
            delay(500) // Simulate some work
            emit(i)
        }
    }
    
    // 2. Flow operators
    fun flowWithOperators(): Flow<String> = flow {
        for (i in 1..10) {
            delay(300)
            emit(i)
        }
    }.filter { it % 2 == 0 } // Only even numbers
        .map { "Processed: $it" } // Transform to string
    
    // 3. Flow transformations
    fun flowWithTransformations(input: Int): Flow<String> = flowOf(input)
        .flatMapConcat { value ->
            flowOf("First: $value", "Second: ${value * 2}")
        }
    
    // 4. Flow context and dispatchers
    fun flowWithContext(): Flow<String> = flow {
        // This code runs in the context specified by flowOn below
        for (i in 1..3) {
            delay(100)
            val threadName = Thread.currentThread().name
            emit("Value: $i on thread: $threadName")
        }
    }.flowOn(Dispatchers.IO) // Change the context to IO dispatcher
    
    // 5. Flow error handling
    fun flowWithErrors(): Flow<String> = flow {
        for (i in 1..5) {
            delay(200)
            if (i == 3) throw IOException("Simulated error at item $i")
            emit("Success: $i")
        }
    }.catch { e -> // Handle errors
        emit("Error caught: ${e.message}")
    }.onCompletion { error ->
        // Called when the flow is completed (either normally or with exception)
        Log.d(TAG, "Flow completed, error: ${error?.message}")
    }
    
    // 6. Flow cancellation example (called from coroutine scope)
    fun cancelableFlow(scope: CoroutineScope): Job = flow {
        for (i in 1..Int.MAX_VALUE) { // Infinite flow
            delay(500)
            emit(i)
        }
    }.onEach { value ->
        Log.d(TAG, "Emitted: $value")
    }.launchIn(scope)
    
    // 7. Flow backpressure
    fun flowWithBackpressure(): Flow<Int> = flow {
        for (i in 1..20) {
            delay(100) // Produce values quickly
            Log.d(TAG, "Emitting $i")
            emit(i)
        }
    }.buffer(5) // Buffer up to 5 elements
    
    // 8. StateFlow example
    private val _stateFlow = MutableStateFlow(0)
    val stateFlow: StateFlow<Int> = _stateFlow.asStateFlow()
    
    fun incrementStateFlow() {
        _stateFlow.value += 1
    }
    
    // 9. SharedFlow example
    private val _sharedFlow = MutableSharedFlow<String>(
        replay = 2, // Keep last 2 values
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val sharedFlow = _sharedFlow
    
    suspend fun emitToSharedFlow(value: String) {
        _sharedFlow.emit(value)
    }
    
    // 10. Flow vs LiveData example - Flow is more versatile
    val flowAsState = flowOf(1, 2, 3)
        .onEach { delay(1000) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    
    // 11. Flow combination
    fun combineFlows(): Flow<String> {
        val flow1 = flowOf("A", "B", "C").onEach { delay(400) }
        val flow2 = flowOf(1, 2, 3).onEach { delay(200) }
        return flow1.zip(flow2) { a, b -> "$a-$b" }
    }
    
    // 12. Flow reduce (terminal operator)
    suspend fun reduceFlow(): Int {
        return flowOf(1, 2, 3, 4, 5)
            .reduce { accumulator, value -> accumulator + value }
    }
    
    // 13. flatMapLatest example (useful for search queries)
    fun searchWithFlatMapLatest(queryFlow: Flow<String>): Flow<String> {
        return queryFlow
            .debounce(300) // Wait for user to stop typing
            .flatMapLatest { query ->
                performSearch(query)
            }
    }
    
    private fun performSearch(query: String): Flow<String> = flow {
        delay(500) // Simulate network request
        emit("Results for: $query")
    }
    
    // 14. Flow retry
    fun flowWithRetry(): Flow<String> = flow {
        var attempts = 0
        while (true) {
            attempts++
            if (attempts <= 3) {
                throw IOException("Simulated network error")
            }
            emit("Success after $attempts attempts")
            break
        }
    }.retry(3) { cause ->
        cause is IOException
    }.onStart {
        emit("Starting flow with retry...")
    }
    
    // 15. Flow collect with timeout
    suspend fun collectWithTimeout() {
        try {
            withTimeout(2000) { // 2 seconds timeout
                flow {
                    for (i in 1..10) {
                        delay(500)
                        emit(i)
                    }
                }.collect { value ->
                    Log.d(TAG, "Collected: $value")
                }
            }
        } catch (e: CancellationException) {
            Log.d(TAG, "Flow collection timed out")
        }
    }
}

class FlowDemoActivity : ComponentActivity() {
    
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, FlowDemoActivity::class.java))
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearnAndroidTheme {
                FlowDemoScreen()
            }
        }
    }
}

@Composable
fun FlowDemoScreen(viewModel: FlowDemoViewModel = viewModel()) {
    val scope = rememberCoroutineScope()
    var outputText by remember { mutableStateOf("") }
    var currentJob by remember { mutableStateOf<Job?>(null) }
    
    // StateFlow demo
    val stateFlowValue by viewModel.stateFlow.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        FakeStatusBar(color = Color(0xFFFFFFFF))
        
        TitleBar(
            title = "Flow Demo",
            backgroundColor = LearnAndroidTheme.themeColors.listItem,
            titleColor = LearnAndroidTheme.themeColors.textPrimary
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Kotlin Flow Examples",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            DemoCard("Basic Flow", "Create and collect from a simple Flow") {
                outputText = ""
                scope.launch {
                    viewModel.createSimpleFlow().collect { value ->
                        outputText += "Collected: $value\n"
                    }
                }
            }
            
            DemoCard("Flow Operators", "Filter and map operators") {
                outputText = ""
                scope.launch {
                    viewModel.flowWithOperators().collect { value ->
                        outputText += "$value\n"
                    }
                }
            }
            
            DemoCard("Flow Transformations", "flatMapConcat example") {
                outputText = ""
                scope.launch {
                    viewModel.flowWithTransformations(5).collect { value ->
                        outputText += "$value\n"
                    }
                }
            }
            
            DemoCard("Flow Context", "Using flowOn to change context") {
                outputText = ""
                scope.launch {
                    viewModel.flowWithContext().collect { value ->
                        outputText += "$value\n"
                    }
                }
            }
            
            DemoCard("Error Handling", "Handle exceptions in Flow") {
                outputText = ""
                scope.launch {
                    viewModel.flowWithErrors().collect { value ->
                        outputText += "$value\n"
                    }
                }
            }
            
            DemoCard("Flow Cancellation", "Start/Cancel a Flow") {
                if (currentJob?.isActive == true) {
                    outputText += "Cancelling flow...\n"
                    currentJob?.cancel()
                    currentJob = null
                } else {
                    outputText = "Starting infinite flow (check logs)...\n"
                    currentJob = viewModel.cancelableFlow(scope)
                }
            }
            
            DemoCard("Flow Combination", "Combine multiple flows") {
                outputText = ""
                scope.launch {
                    viewModel.combineFlows().collect { value ->
                        outputText += "Combined: $value\n"
                    }
                }
            }
            
            DemoCard("StateFlow", "StateFlow example: $stateFlowValue") {
                viewModel.incrementStateFlow()
            }
            
            DemoCard("SharedFlow", "Emit to SharedFlow") {
                outputText = ""
                scope.launch {
                    // First collect
                    launch {
                        viewModel.sharedFlow.collect { value ->
                            outputText += "Collector 1: $value\n"
                        }
                    }
                    
                    // Emit some values
                    viewModel.emitToSharedFlow("First event")
                    delay(300)
                    
                    // Second collector (will receive replayed values)
                    launch {
                        viewModel.sharedFlow.collect { value ->
                            outputText += "Collector 2: $value\n"
                        }
                    }
                    
                    viewModel.emitToSharedFlow("Second event")
                }
            }
            
            DemoCard("Flow Collection Modes", "Latest vs. Collect") {
                outputText = ""
                scope.launch {
                    // Regular collect
                    launch {
                        viewModel.createSimpleFlow()
                            .onEach { delay(1000) } // Slow consumer
                            .collect { value ->
                                outputText += "Regular collect: $value\n"
                            }
                    }
                    
                    // collectLatest
                    launch {
                        viewModel.createSimpleFlow()
                            .onEach { delay(1000) } // Slow consumer
                            .collectLatest { value ->
                                outputText += "Starting work on: $value\n"
                                delay(1500) // Long operation
                                outputText += "Finished work on: $value\n"
                            }
                    }
                }
            }
            
            // Output area
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE))
            ) {
                Text(
                    text = outputText.ifEmpty { "Output will appear here" },
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black
                )
            }
        }
    }
}
