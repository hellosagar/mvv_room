package com.example.roompractice

import android.util.Log
import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roompractice.db.Subscriber
import com.example.roompractice.db.SubscriberRepository
import kotlinx.coroutines.launch

class SubscriberViewModel(val repository: SubscriberRepository) : ViewModel(), Observable {

    val subscribers = repository.subscribers
    private var isUpdateOrDelete: Boolean = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtontext = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMesage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMesage


    init {
        saveOrUpdateButtontext.value = "Save"
        clearAllOrDeleteButtonText.value = "Delete all"
    }

    fun saveOrUpdate() {

        if (inputName.value == null) {
            statusMesage.value = Event("Please Enter Subscriber's Name")
        } else if (inputEmail.value == null) {
            statusMesage.value = Event("Please Enter Subscriber's Email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMesage.value = Event("Please Enter a Correct Email Address")
        } else {
            if (isUpdateOrDelete) {
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!
                update(subscriberToUpdateOrDelete)
            } else {
                val name = inputName.value!!
                val email = inputEmail.value!!
                insert(Subscriber(0, name, email))
                inputName.value = null
                inputEmail.value = null
            }
        }
    }

    fun clearOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }

    }

    fun insert(subscriber: Subscriber) = viewModelScope.launch {
        val newRowId = repository.insert(subscriber)
        if (newRowId > -1) {
            statusMesage.value = Event("$newRowId Row No. Subscriber Inserted Successfully! ")
        } else {
            statusMesage.value = Event("Error Occured!")
        }
    }

    private fun update(subscriber: Subscriber) {
        viewModelScope.launch {
            repository.update(subscriber)
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtontext.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMesage.value = Event("Subscriber Updated Successfully!")
        }
    }

    private fun delete(subscriber: Subscriber) = viewModelScope.launch {
        repository.delete(subscriber)
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtontext.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
        statusMesage.value = Event("Subscriber Deleted Successfully!")
    }

    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
        Log.d("Thread Name", Thread.currentThread().name)
        statusMesage.value = Event("All Subscribers Deleted Successfully!")
    }

    fun initUpdateOrDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtontext.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}