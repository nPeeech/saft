package io.github.npeeech.saft

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalTime

class SharedViewModel(receiveText: String) : ViewModel() {
    val plainText = MutableLiveData<String>()
    val richText = MutableLiveData<SpannableString>()

    private var alarmList = MutableLiveData<List<LocalTime>>()
    private var deltaTimeList: List<Int> = listOf()

    private val _eventParse = MutableLiveData<Boolean>()
    val eventParse: LiveData<Boolean>
        get() = _eventParse

    val deltaTimePosition = MutableLiveData<Int>()

    private val _setAlarmPosition = MutableLiveData(-1)
    val setAlarmPosition: LiveData<Int>
        get() = _setAlarmPosition

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
        val tmpAlarmList = mutableListOf<LocalTime>()

        richText.value = SpannableString(plainText.value)
        regex.findAll(plainText.value ?: "").withIndex().forEach {
            richText.value?.setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        Log.i("observe", "クリックされた")

                        _setAlarmPosition.value = it.index
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = Color.BLUE
                    }
                },
                it.value.range.start,
                it.value.range.endInclusive + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tmpAlarmList.add(LocalTime.parse(it.value.value))
        }
        alarmList.value = tmpAlarmList
    }

    fun onSetAlarmPositionComplete() {
        _setAlarmPosition.value = -1
    }

    fun calcAlarm(alarmTime: LocalTime): LocalTime {
        return alarmTime.plusMinutes(deltaTimeList[deltaTimePosition.value ?: 0].toLong())
    }

    fun calcAlarm(position: Int): LocalTime {
        val alarmTime = alarmList.value?.get(position)!!
        return calcAlarm(alarmTime)
    }

    fun setDeltaTimeList(newDeltaTimeList: List<Int>) {
        deltaTimeList = newDeltaTimeList
    }
}
