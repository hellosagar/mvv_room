package com.example.roompractice

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roompractice.databinding.ActivityMainBinding
import com.example.roompractice.db.Subscriber
import com.example.roompractice.db.SubscriberDatabase
import com.example.roompractice.db.SubscriberRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var viewModel:SubscriberViewModel
    private lateinit var adapter:RecyclerviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(this).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModeFactory(repository)
        viewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        initRecyclerview()

        viewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerview(){
        binding.susbsriberRecyclerview.layoutManager = LinearLayoutManager(this)
        displaySubscribersList()
        adapter = RecyclerviewAdapter ({selectedSubscriber:Subscriber -> listItemClick(selectedSubscriber)})
        binding.susbsriberRecyclerview.adapter = adapter
        displaySubscribersList()
    }

    private fun displaySubscribersList(){
        viewModel.subscribers.observe(this, Observer {
            Log.d("List Of Items",it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClick(subscriber:Subscriber){
        viewModel.initUpdateOrDelete(subscriber)
    }

}