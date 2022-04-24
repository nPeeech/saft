package io.github.npeeech.saft

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel(receiveText: String) : ViewModel() {
    val plainText = MutableLiveData<String>()

    private val _eventParse = MutableLiveData<Boolean>()
    val eventParse: LiveData<Boolean>
        get() = _eventParse


    init {
        plainText.value = receiveText
    }

    fun onParse(){
        _eventParse.value = true
    }

    fun onParseComplete(){
        _eventParse.value = false
    }
    //TODO 引数で文字列を受け取るようにしたけど，ChoiceFragmentで困らない？
}