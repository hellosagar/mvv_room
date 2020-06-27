package com.example.roompractice

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roompractice.db.Subscriber
import com.example.roompractice.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SubscriberViewModel(val repository: SubscriberRepository) : ViewModel(),Observable {

    val subscribers = repository.subscribers

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtontext = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtontext.value = "Save"
        clearAllOrDeleteButtonText.value = "Delete all"
    }


    fun saveOrUpdate() {
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Subscriber(0,name,email))
        inputName.value = null
        inputEmail.value = null
    }

    fun clearOrDelete() {
        clearAll()
    }

    fun insert(subscriber: Subscriber) = viewModelScope.launch(IO) {
        repository.insert(subscriber)
    }

    fun update(subscriber: Subscriber) = viewModelScope.launch(IO) {
        repository.update(subscriber)
    }

    fun clearAll() = viewModelScope.launch(IO) {
        repository.deleteAll()
        Log.d("Thread Name",Thread.currentThread().name)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}