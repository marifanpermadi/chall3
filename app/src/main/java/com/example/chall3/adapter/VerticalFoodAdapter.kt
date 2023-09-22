package com.example.chall3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chall3.R
import com.example.chall3.model.Foods
import com.google.android.material.button.MaterialButton

class VerticalFoodAdapter(
    private val listFood: ArrayList<Foods>,
    var isGridMode: Boolean = true,
    private var onItemClick: ((Foods) -> Unit)? = null
) : RecyclerView.Adapter<VerticalFoodAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutResId = if (isGridMode) R.layout.item_vertical else R.layout.item_vertical_linear
        val view: View = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listFood.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val (name, price, photo, _, star) = listFood[position]
        holder.imgPhoto.setImageResource(photo)
        holder.imgName.text = name
        holder.price.text = price.toString()
        holder.imgStar.text = star

        val currentItem = listFood[position]
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }

    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.iv_food)
        val price: TextView = itemView.findViewById(R.id.tv_price)
        val imgName: TextView = itemView.findViewById(R.id.tv_desc)
        val imgStar: MaterialButton = itemView.findViewById(R.id.bt_star)

    }

}