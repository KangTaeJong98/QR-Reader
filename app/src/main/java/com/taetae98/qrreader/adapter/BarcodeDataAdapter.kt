package com.taetae98.qrreader.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.modules.library.base.BaseViewHolder
import com.taetae98.modules.library.binding.BindingRecyclerViewAdapter
import com.taetae98.modules.library.binding.BindingViewHolder
import com.taetae98.qrreader.databinding.HolderBarcodeDataBinding
import com.taetae98.qrreader.dto.BarcodeData

class BarcodeDataAdapter : BindingRecyclerViewAdapter<BarcodeData>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<BarcodeData>() {
            override fun areItemsTheSame(oldItem: BarcodeData, newItem: BarcodeData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BarcodeData, newItem: BarcodeData): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BarcodeData> {
        return BarcodeDataViewHolder(
            HolderBarcodeDataBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class BarcodeDataViewHolder(binding: HolderBarcodeDataBinding) : BindingViewHolder<BarcodeData, HolderBarcodeDataBinding>(binding) {
        init {
            binding.setOnBookmark {
                Toast.makeText(context, "Click", Toast.LENGTH_LONG).show()
            }
        }
        override fun onBindViewDataBinding() {
            super.onBindViewDataBinding()
            binding.barcodeData = item
        }
    }
}