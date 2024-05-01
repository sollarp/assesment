package com.envitia.exercise

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.envitia.exercise.databinding.ActivityMainBinding
import com.envitia.exercise.util.Constants.TEXT_EMPTY
import com.envitia.exercise.util.TextProcessingUtils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CustomAdapter(ArrayList())
        recyclerView.adapter = adapter

        mainViewModel.timeAndTextList.observe(this) { items ->
            adapter.updateData(items)
        }

        binding.button.setOnClickListener {
            processText()
        }
    }

    private fun processText() {
        val text = binding.myTextField.text.toString()
        if (text.isNotBlank()) {
            mainViewModel.addUserText(text)
            TextProcessingUtils.saveToFile(text, this)
            binding.myTextField.text.clear()
        } else {
            Toast.makeText(this, TEXT_EMPTY, Toast.LENGTH_SHORT).show()
        }
    }
}
