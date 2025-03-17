package com.example.learnandroid.vm


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.net.RetrofitInstance
import kotlinx.coroutines.launch

/**
 * @author zhuhao
 * @date  18:09
 **/
class MainVM : ViewModel() {
    var wishList by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        fetchWishData()
    }

    private fun fetchWishData() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getWishData()
                wishList = response
            } catch (e: Exception) {
                Log.e("error", e.message.toString())
                // Handle error
            }
        }
    }
}
