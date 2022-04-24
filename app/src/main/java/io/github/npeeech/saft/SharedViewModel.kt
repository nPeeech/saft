package io.github.npeeech.saft

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel(receiveText: String) : ViewModel() {
    val plainText = MutableLiveData<String>()

    init {
        plainText.value = receiveText
    }
    //TODO 引数で文字列を受け取るようにしたけど，ChoiceFragmentで困らない？
}