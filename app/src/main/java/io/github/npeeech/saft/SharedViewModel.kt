package io.github.npeeech.saft

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.time.LocalTime
import java.util.stream.Collectors

class SharedViewModel(receiveText: String) : ViewModel() {
    val plainText = MutableLiveData<String>()

    private val _eventParse = MutableLiveData<Boolean>()
    val eventParse: LiveData<Boolean>
        get() = _eventParse

    val deltaTimePosition = MutableLiveData<Int>()

    var alarmList = MutableLiveData<List<LocalTime>>()

    var deltaTimeList: List<Int> = listOf()


    init {
        plainText.value = receiveText
    }

    fun onParse() {
        _eventParse.value = true
    }

    fun onParseComplete() {
        _eventParse.value = false
    }

    fun parse() {
        val regex = Regex("""[0-2]?[0-9][:][0-6][0-9]""")

        alarmList.value = regex.findAll(plainText.value ?: "").map {
            LocalTime.parse(it.value)
        }.toSet().toList().sorted()
    }

    fun calcAlarm(alarmTime: LocalTime): LocalTime {
        return alarmTime.plusMinutes(deltaTimeList[deltaTimePosition.value ?: 0].toLong())
    }
    fun calcAlarm(position: Int): LocalTime{
        val alarmTime = alarmList.value?.get(position)!!
        return calcAlarm(alarmTime)
    }
}