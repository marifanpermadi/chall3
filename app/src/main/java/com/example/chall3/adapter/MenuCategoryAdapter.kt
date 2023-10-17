package com.example.chall3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chall3.R
import com.example.chall3.data.apimodel.DataCategory

class MenuCategoryAdapter(
    private val listCategory: List<DataCategory>,
    private val onCategoryClickListener: (String) -> Unit
) : RecyclerView.Adapter<MenuCategoryAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listCategory.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val category = listCategory[position]

        Glide.with(holder.itemView.context)
            .load(category.imageUrl)
            .centerCrop()
            .into(holder.imgPhoto)

        holder.imgName.text = category.nama

        holder.itemView.setOnClickListener {
            onCategoryClickListener(category.nama)
        }

    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.iv_food)
        val imgName: TextView = itemView.findViewById(R.id.tv_desc)
    }
}