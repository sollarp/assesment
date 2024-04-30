package com.envitia.exercise

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.envitia.exercise.Constants.TEXT_EMPTY
import com.envitia.exercise.Constants.TEXT_FILE_NAME
import com.envitia.exercise.databinding.ActivityMainBinding
import java.io.IOException

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
            val text = binding.myTextField.text.toString()
            if (text.isNotBlank()) {
                mainViewModel.addUserText(text)
                saveToFile(text)
                binding.myTextField.text.clear()
            } else {
                Toast.makeText(this, TEXT_EMPTY, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveToFile(text: String) {
        try {
            applicationContext.openFileOutput(TEXT_FILE_NAME, Context.MODE_APPEND).use { outputStream ->
                outputStream.write("$text\n\n".toByteArray())
                Toast.makeText(applicationContext, "Wrote to file: $TEXT_FILE_NAME", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}
