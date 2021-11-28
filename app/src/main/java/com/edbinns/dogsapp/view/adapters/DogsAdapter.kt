package com.edbinns.dogsapp.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edbinns.dogsapp.databinding.ItemDogBinding
import com.edbinns.dogsapp.models.Dog
import com.edbinns.dogsapp.utils.splitBreed
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.util.ArrayList

class DogsAdapter(private val itemClickListener: ItemClickListener<Dog>) :
    RecyclerView.Adapter<DogsAdapter.DogsViewHolder>() {


    private var imagesList: ArrayList<Dog> = ArrayList()

    private var context: Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder {
        context = parent.context
        val binding = ItemDogBinding.inflate(LayoutInflater.from(context), parent, false)
        return DogsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogsViewHolder, position: Int) {
        holder.setImage(position)
    }

    override fun getItemCount() = imagesList.size


    fun updateData(data: List<Dog>) {
        var newList = emptyList<Dog>()

        if(!imagesList.isNullOrEmpty()){
            imagesList.forEach {  item ->
                newList = data.filter { dog -> dog.imageURL != item.imageURL}
            }
            imagesList.addAll(newList)
        }else{
            imagesList.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun deleteData() {
        imagesList.clear()
        notifyDataSetChanged()
    }


    inner class DogsViewHolder(val binding: ItemDogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setImage(position: Int) {
            val dog = imagesList[position]
            context?.let {
                val picasso = Picasso.get()
                picasso.load(dog.imageURL)
                    .into(binding.ivDogPhoto)

                binding.tvDogBreed.text = dog.splitBreed()
            }
            binding.itemDog.setOnClickListener {
                itemClickListener.onCLickListener(dog)
            }
        }
    }

}