package com.envitia.exercise

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.ui.Modifier
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
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MainActivity
        }

        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val recyclerView = binding.recyclerView
        //recyclerView.layoutManager = LinearLayoutManager(this)

        /*    val adapter = CustomAdapter(ArrayList())
        recyclerView.adapter = adapter*/
        mainViewModel.timeAndTextList.observe(this) { item ->
            //adapter.updateData(items)
            recyclerView.setContent {
                LazyColumn(Modifier.fillMaxSize()) {
                    // We use a LazyColumn since the layout manager of the RecyclerView is a vertical LinearLayoutManager
                    items(item.size) {
                        ComposeListView(item[it].text)
                    }
                }
            }
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