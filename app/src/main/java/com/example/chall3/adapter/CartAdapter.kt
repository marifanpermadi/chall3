package com.example.chall3.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

        holder.btPlus.setOnClickListener {
            val newAmount = currentItem.orderAmount + 1
            currentItem.orderAmount = newAmount

            cartViewModel.updateCart(currentItem)
            holder.tvNumber.text = newAmount.toString()

            currentItem.foodPrice = currentItem.basePrice * newAmount
            holder.tvPrice.text = currentItem.foodPrice.toString()
        }

        holder.btMin.setOnClickListener {
            if (currentItem.orderAmount > 1) {
                val newAmount = currentItem.orderAmount - 1
                currentItem.orderAmount = newAmount
                currentItem.foodPrice = currentItem.basePrice * newAmount

                cartViewModel.updateCart(currentItem)
                holder.tvNumber.text = newAmount.toString()

                currentItem.foodPrice = currentItem.basePrice * newAmount
                holder.tvPrice.text = currentItem.foodPrice.toString()
            }
        }
    }

    class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val ivDelete: ImageView = itemView.findViewById(R.id.iv_delete)
        val btPlus: Button = itemView.findViewById(R.id.bt_plus)
        val btMin: Button = itemView.findViewById(R.id.bt_min)
        val tvNumber: TextView = itemView.findViewById(R.id.tv_number)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_price)

        fun bind(cartItem: Cart) {

            Glide.with(itemView.context)
                .load(cartItem.foodImage)
                .into(binding.ivFood)

            binding.tvDesc.text = cartItem.foodName
            binding.tvPrice.text = cartItem.basePrice.toString()
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
        Snackbar.make(view, "Item removed from the cart", Snackbar.LENGTH_SHORT).show()
    }

}
