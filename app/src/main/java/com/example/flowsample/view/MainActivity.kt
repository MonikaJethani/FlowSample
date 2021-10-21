package com.example.flowsample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.flowsample.R
import com.example.flowsample.databinding.ActivityMainBinding
import com.example.flowsample.viewmodel.CommentsViewModel

class MainActivity : AppCompatActivity() {

    // create a CommentsViewModel
    // variable to initialize it later
    private lateinit var viewModel: CommentsViewModel

    // create a view binding variable
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // instantiate view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize viewModel
        viewModel = ViewModelProvider(this).get(CommentsViewModel::class.java)


        // Listen for the button click event to search
        binding.button.setOnClickListener {

            // check to prevent api call with no parameters
            if (binding.searchEditText.text.isNullOrEmpty()) {
                Toast.makeText(this, "Query Can't be empty", Toast.LENGTH_SHORT).show()
            } else {
                // if Query isn't empty, make the api call
                viewModel.getNewComment(binding.searchEditText.text.toString().toInt())
            }
        }
    }
}