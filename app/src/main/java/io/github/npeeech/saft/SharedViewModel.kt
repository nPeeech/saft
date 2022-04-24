package io.github.npeeech.saft

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalTime

class SharedViewModel(receiveText: String) : ViewModel() {
    val plainText = MutableLiveData<String>()

    private val _eventParse = MutableLiveData<Boolean>()
    val eventParse: LiveData<Boolean>
        get() = _eventParse

    var alarmList: List<LocalTime> = listOf()


    init {
        plainText.value = receiveText
    }

    fun onParse(){
        _eventParse.value = true
    }

    fun onParseComplete(){
        _eventParse.value = false
    }

    fun parse(){
        val regex = Regex("""[0-2]?[0-9][:時][0-6][0-9][分]?""")

        alarmList = regex.findAll(plainText.value ?: "").map {
            LocalTime.parse(it.value)
        }.toSet().toList().sorted()
    }
}