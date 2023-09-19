package com.example.chall3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VerticalFoodAdapter(
    private val listFood: ArrayList<Foods>,
    private val isGridMode: Boolean = true
) : RecyclerView.Adapter<VerticalFoodAdapter.ListViewHolder>() {

    var onItemClick: ((Foods) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutResId = if (isGridMode) R.layout.item_vertical else R.layout.item_vertical_linear
        val view: View =
            LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listFood.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, price, photo) = listFood[position]
        holder.imgPhoto.setImageResource(photo)
        holder.imgName.text = name
        holder.price.text = price

        val currentItem = listFood[position]

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.iv_food)
        val price: TextView = itemView.findViewById(R.id.tv_price)
        val imgName: TextView = itemView.findViewById(R.id.tv_desc)

    }


}