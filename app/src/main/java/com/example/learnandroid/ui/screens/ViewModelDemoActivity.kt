package com.example.learnandroid.ui.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.learnandroid.databinding.ActivityViewModelDemoBinding
import com.example.learnandroid.vm.ViewModelDemoViewModel

class ViewModelDemoActivity : AppCompatActivity() {

    // Declare the ViewModel
    private lateinit var viewModel: ViewModelDemoViewModel

    // UI components
    private lateinit var binding: ActivityViewModelDemoBinding

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ViewModelDemoActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize data binding properly
        binding = ActivityViewModelDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the ViewModel
        viewModel = ViewModelProvider(this)[ViewModelDemoViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this  // Enable LiveData observation in binding
    }
}