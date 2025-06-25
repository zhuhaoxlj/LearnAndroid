package com.example.learnandroid.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Demo ViewModel that shows basic ViewModel functionality with LiveData
 */
class ViewModelDemoViewModel : ViewModel() {
    
    // Counter value as LiveData to be observed
    private val _counter = MutableLiveData<Int>(0)
    val counter: LiveData<Int> = _counter
    
    // Status message as LiveData
    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> = _statusMessage
    
    // Simulated loading state
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading
    
    /**
     * Increments the counter value
     */
    fun incrementCounter() {
        _counter.value = (_counter.value ?: 0) + 1
        updateStatus("Counter increased to ${_counter.value}")
    }
    
    /**
     * Decrements the counter value
     */
    fun decrementCounter() {
        _counter.value = (_counter.value ?: 0) - 1
        updateStatus("Counter decreased to ${_counter.value}")
    }
    
    /**
     * Resets the counter value
     */
    fun resetCounter() {
        _counter.value = 0
        updateStatus("Counter reset to zero")
    }
    
    /**
     * Simulates a network call with loading state
     */
    fun performDataLoading() {
        _isLoading.value = true
        _statusMessage.value = "Loading data..."
        
        // Simulate network delay
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            _isLoading.value = false
            val randomValue = (0..100).random()
            _counter.value = randomValue
            _statusMessage.value = "Data loaded! Random value: $randomValue"
        }, 2000)
    }
    
    private fun updateStatus(message: String) {
        _statusMessage.value = message
    }
    
    override fun onCleared() {
        super.onCleared()
        // Clean up any resources here if needed
    }
} 