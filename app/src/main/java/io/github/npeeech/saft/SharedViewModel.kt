package io.github.npeeech.saft

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel(receiveText: String) : ViewModel() {
    private val _plainText = MutableLiveData<String>()
    val plainText: LiveData<String>
        get() = _plainText

    init {
        _plainText.value = receiveText
    }
    //TODO 引数で文字列を受け取るようにしたけど，ChoiceFragmentで困らない？
}