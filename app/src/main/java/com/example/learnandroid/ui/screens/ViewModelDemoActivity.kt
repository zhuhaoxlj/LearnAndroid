package com.example.learnandroid.ui.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.learnandroid.R
import com.example.learnandroid.databinding.ActivityViewModelDemoBinding
import com.example.learnandroid.vm.ViewModelDemoViewModel

class ViewModelDemoActivity : AppCompatActivity() {

    // Declare the ViewModel
    private lateinit var viewModel: ViewModelDemoViewModel

    // UI components
    private lateinit var counterTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var decrementButton: Button
    private lateinit var resetButton: Button
    private lateinit var loadDataButton: Button
    private lateinit var progressBar: ProgressBar
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ViewModelDemoActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model_demo)

        // Initialize the ViewModel
        viewModel = ViewModelProvider(this)[ViewModelDemoViewModel::class.java]
        // Find views
        counterTextView = findViewById(R.id.textViewCounter)
        statusTextView = findViewById(R.id.textViewStatus)
        decrementButton = findViewById(R.id.buttonDecrement)
        resetButton = findViewById(R.id.buttonReset)
        loadDataButton = findViewById(R.id.buttonLoadData)
        progressBar = findViewById(R.id.progressBar)

        // Set up button click listeners
        decrementButton.setOnClickListener { viewModel.decrementCounter() }
        resetButton.setOnClickListener { viewModel.resetCounter() }
        loadDataButton.setOnClickListener { viewModel.performDataLoading() }

        // Observe LiveData from ViewModel
        setupObservers()
    }

    private fun setupObservers() {
        // Observe counter value
        viewModel.counter.observe(this) { counterValue ->
            counterTextView.text = counterValue.toString()
        }

        // Observe status message
        viewModel.statusMessage.observe(this) { message ->
            statusTextView.text = message
        }

        // Observe loading state
        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            loadDataButton.isEnabled = !isLoading
        }
    }
} 