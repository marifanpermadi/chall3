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
import com.example.chall3.data.apimodel.DataMenu
import com.example.chall3.data.apimodel.ListMenuResponse
import com.example.chall3.utils.MenuDiffUtil

class MenuAdapterDI(
    var isGridMode: Boolean = true,
    private val listener: OnItemClickListener? = null
) : RecyclerView.Adapter<MenuAdapterDI.MyViewHolder>() {

    private var menu = emptyList<DataMenu>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val menuImage: ImageView = itemView.findViewById(R.id.iv_food)
        private val menuPrice: TextView = itemView.findViewById(R.id.tv_price)
        private val menuName: TextView = itemView.findViewById(R.id.tv_desc)

        fun bind(data: DataMenu) {
            Glide.with(itemView.context)
                .load(data.imageUrl)
                .into(menuImage)

            menuPrice.text = data.harga.toString()
            menuName.text = data.nama

        }
    }

    override fun onBindViewHolder(holder: MenuAdapterDI.MyViewHolder, position: Int) {
        val currentMenu = menu[position]
        holder.bind(currentMenu)
        holder.itemView.setOnClickListener {
            listener?.onItemClick(currentMenu)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutResId =
            if (isGridMode) R.layout.item_vertical_grid else R.layout.item_vertical_linear
        val view: View = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menu.size
    }

    interface OnItemClickListener {
        fun onItemClick(data: DataMenu)
    }

    fun setData(newData: ListMenuResponse) {
        val menuDiffUtil =
            MenuDiffUtil(menu, newData.data)
        val diffUtilResult = DiffUtil.calculateDiff(menuDiffUtil)
        menu = newData.data
        diffUtilResult.dispatchUpdatesTo(this)

    }

    fun clearData() {
        val diffCallback = MenuDiffUtil(menu, emptyList())
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        menu = emptyList()
        diffResult.dispatchUpdatesTo(this)
    }

}