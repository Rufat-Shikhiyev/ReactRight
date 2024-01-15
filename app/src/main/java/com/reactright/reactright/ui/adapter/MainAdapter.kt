package com.reactright.reactright.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.reactright.reactright.R
import com.reactright.reactright.data.entity.Items
import com.reactright.reactright.databinding.CardDesignItemBinding
import com.reactright.reactright.ui.fragments.MainFragment
import com.reactright.reactright.ui.fragments.MainFragmentDirections
import com.reactright.reactright.util.go
import com.squareup.picasso.Picasso

class MainAdapter(var context: Context, var itemList:List<Items>)
    : RecyclerView.Adapter<MainAdapter.CardDesignHolder>(){

    inner class CardDesignHolder(var binding: CardDesignItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
        val binding = CardDesignItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CardDesignHolder(binding)
    }

    override fun onBindViewHolder(holder: CardDesignHolder, position: Int) {
        val item = itemList[position]
        val bind = holder.binding

        bind.title.text = item.title
        bind.describe.text = item.describe

        bind.buttonMore.setOnClickListener {
            val transition = MainFragmentDirections.mainToDetails(item)
            Navigation.go(it, transition)
        }

//        Picasso.get().load(item.mainImage).into(bind.image)

        if (item.mainImage.isNotEmpty()) {
        Glide.with(context).load(item.mainImage).placeholder(R.drawable.add_post).into(bind.image)
        } else {
            // Handle the case where the image path is empty or null
            bind.image.setImageResource(R.drawable.add_post)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}