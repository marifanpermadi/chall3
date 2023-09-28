package com.example.chall3.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.chall3.R
import com.example.chall3.database.Cart
import com.example.chall3.databinding.ItemCartBinding
import com.example.chall3.viewmodel.CartViewModel
import com.google.android.material.snackbar.Snackbar

class CartAdapter(
    private val cartViewModel: CartViewModel
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartItems: List<Cart> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = cartItems[position]
        holder.bind(currentItem)

        holder.ivDelete.setOnClickListener {
            cartViewModel.deleteCartItemById(currentItem.id)
            showSnackBar(holder.itemView)
        }
    }

    class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val ivDelete: ImageView = itemView.findViewById(R.id.iv_delete)

        fun bind(cartItem: Cart) {
            binding.tvDesc.text = cartItem.foodName
            binding.ivFood.setImageResource(cartItem.foodImage)
            binding.tvPrice.text = cartItem.foodPrice.toString()
            binding.tvNote.text = cartItem.orderNote
            binding.tvNumber.text = cartItem.orderAmount.toString()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(cartItems: List<Cart>) {
        this.cartItems = cartItems
        notifyDataSetChanged()
    }

    private fun showSnackBar(view: View) {
        Snackbar.make(view, "Item removed from the chart", Snackbar.LENGTH_SHORT).show()
    }

}
