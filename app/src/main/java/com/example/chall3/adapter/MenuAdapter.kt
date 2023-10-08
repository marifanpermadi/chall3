package com.example.chall3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chall3.R
import com.example.chall3.data.apimodel.DataMenu
import com.example.chall3.databinding.ItemVerticalLinearBinding
import com.example.chall3.model.Menu
import com.example.chall3.ui.fragment.DetailFragmentDirections
import com.example.chall3.ui.fragment.HomeFragmentDirections

class MenuAdapter(
    var isGridMode: Boolean = true,
) : PagingDataAdapter<DataMenu, MenuAdapter.MyViewHolder>(DIFF_CALLBACK) {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuImage: ImageView = itemView.findViewById(R.id.iv_food)
        val menuPrice: TextView = itemView.findViewById(R.id.tv_price)
        val menuName: TextView = itemView.findViewById(R.id.tv_desc)

        fun bind(data: DataMenu) {
            Glide.with(itemView.context)
                .load(data.imageUrl)
                .into(menuImage)

            menuPrice.text = data.harga.toString()
            menuName.text = data.nama

            /*itemView.setOnClickListener {
                 onItemClick(data)
            }

            itemView.setOnClickListener {
                val menu = Menu (
                    data.alamatResto,
                    data.detail,
                    data.harga,
                    data.imageUrl,
                    data.nama
                )
            }

            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()*/
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutResId =
            if (isGridMode) R.layout.item_vertical_grid else R.layout.item_vertical_linear
        val view: View = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return MyViewHolder(view)
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataMenu>() {
            override fun areItemsTheSame(oldItem: DataMenu, newItem: DataMenu): Boolean {
                return oldItem.nama == newItem.nama
            }

            override fun areContentsTheSame(oldItem: DataMenu, newItem: DataMenu): Boolean {
                return oldItem == newItem
            }
        }
    }
}