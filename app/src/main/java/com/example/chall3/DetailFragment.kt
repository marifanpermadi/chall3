package com.example.chall3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chall3.databinding.FragmentDetailBinding
import com.example.chall3.databinding.FragmentHomeBinding


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val item = arguments?.getParcelable<Foods>("item")

        item?.let {
            binding.ivImage.setImageResource(it.photo)
            binding.tvFoodPrice.text = item.price.toString()
            binding.tvFoodName.text = item.name
            binding.tvDesc.text = item.description
        }


        return binding.root
    }

    companion object {
        fun newInstance(item: Foods): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putParcelable("item", item)
            fragment.arguments = args
            return fragment
        }
    }

}