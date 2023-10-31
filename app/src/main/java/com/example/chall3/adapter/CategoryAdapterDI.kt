package com.example.chall3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chall3.R
import com.example.chall3.data.apimodel.CategoryResponse
import com.example.chall3.data.apimodel.DataCategory
import com.example.chall3.utils.MenuDiffUtil

class CategoryAdapterDI(
    private val onCategoryClickListener: ((String) -> Unit)? = null
) : RecyclerView.Adapter<CategoryAdapterDI.MyViewHolder>() {

    private var category = emptyList<DataCategory>()
    private var selectedCategory: String? = null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgPhoto: ImageView = itemView.findViewById(R.id.iv_food)
        private val imgName: TextView = itemView.findViewById(R.id.tv_desc)

        fun bind(data: DataCategory) {
            Glide.with(itemView.context)
                .load(data.imageUrl)
                .into(imgPhoto)

            imgName.text = data.nama

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = category.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentCategory = category[position]
        holder.bind(currentCategory)

        holder.itemView.setOnClickListener {
            selectedCategory = currentCategory.nama
            onCategoryClickListener?.invoke(selectedCategory!!)
        }
    }

    fun setData(newData: CategoryResponse) {
        val menuDiffUtil =
            MenuDiffUtil(category, newData.data)
        val diffUtilResult = DiffUtil.calculateDiff(menuDiffUtil)
        category = newData.data
        diffUtilResult.dispatchUpdatesTo(this)

    }

}