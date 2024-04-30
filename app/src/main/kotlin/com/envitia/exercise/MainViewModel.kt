package com.envitia.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.envitia.exercise.model.TimeAndText
import java.text.DateFormat

class MainViewModel : ViewModel() {

    private val _timeAndTextList = MutableLiveData<ArrayList<TimeAndText>>(ArrayList())
    val timeAndTextList: LiveData<ArrayList<TimeAndText>>
        get() = _timeAndTextList

    private val addNewText: MutableLiveData<TimeAndText> by lazy {
        MutableLiveData<TimeAndText>()
    }

    private fun addToList() {
        timeAndTextList.value?.add(addNewText.value!!)
        _timeAndTextList.value?.let { updateData(it) }
    }

    private fun updateData(newData: ArrayList<TimeAndText>) {
        _timeAndTextList.postValue(newData)
    }

    fun addUserText(text: String) {
        val currentTime = System.currentTimeMillis()
        val formattedTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(currentTime)
        addNewText.value = TimeAndText(formattedTime.toString(), text)
        addToList()
    }
}