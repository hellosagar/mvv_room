package com.example.roompractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roompractice.databinding.ItemListBinding
import com.example.roompractice.db.Subscriber

class RecyclerviewAdapter(
    private val clickListner: ((Subscriber) -> Unit)
) : RecyclerView.Adapter<MyViewHolder>() {

    private val subscribersList= ArrayList<Subscriber>()

    fun setList(subscribers: List<Subscriber>){
        subscribersList.clear()
        subscribersList.addAll(subscribers)
    }

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
        return subscribersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribersList[position],clickListner)
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