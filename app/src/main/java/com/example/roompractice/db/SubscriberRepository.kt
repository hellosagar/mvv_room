package com.example.roompractice.db

import android.util.Log

class SubscriberRepository(private val dao:SubscriberDAO) {

    val subscribers = dao.getAllSubscribers()

    suspend fun insert(subscriber:Subscriber):Long{
        return dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber:Subscriber){
        dao.updateSubscriber(subscriber)
        Log.d("Thread Name",Thread.currentThread().name)
    }

    suspend fun delete(subscriber:Subscriber){
        dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }

}