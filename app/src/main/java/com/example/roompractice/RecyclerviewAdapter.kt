package com.example.roompractice

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.MultiAutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roompractice.databinding.ItemListBinding
import com.example.roompractice.db.Subscriber

class RecyclerviewAdapter(
    private val subscribers: List<Subscriber>,
    private val clickListner: ((Subscriber) -> Unit)
) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflator = LayoutInflater.from(parent.context)
        return MyViewHolder(
            DataBindingUtil.inflate(
                layoutInflator,
                R.layout.item_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribers[position],clickListner)
    }


}

class MyViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(subscriber: Subscriber,clickListner: ((Subscriber) -> Unit)) {
        binding.nameTextViewItem.text = subscriber.name
        binding.emailTextViewItem.text = subscriber.email

        binding.parentItem.setOnClickListener {
            clickListner(subscriber)
        }
    }

}