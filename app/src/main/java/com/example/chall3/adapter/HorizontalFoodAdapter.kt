package com.example.chall3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chall3.R
import com.example.chall3.model.MenuCategory

class HorizontalFoodAdapter(
    private val listFood: ArrayList<MenuCategory>,
    private val onCategoryClickListener: (String) -> Unit
) : RecyclerView.Adapter<HorizontalFoodAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listFood.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, image) = listFood[position]
        holder.imgPhoto.setImageResource(image)
        holder.imgName.text = name

        holder.itemView.setOnClickListener {
            onCategoryClickListener(name)
        }

    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.iv_food)
        val imgName: TextView = itemView.findViewById(R.id.tv_desc)
    }
}