package com.taetae98.qrreader.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.modules.library.base.BaseViewHolder
import com.taetae98.modules.library.binding.BindingRecyclerViewAdapter
import com.taetae98.modules.library.binding.BindingViewHolder
import com.taetae98.qrreader.application.TAG
import com.taetae98.qrreader.databinding.HolderBarcodeDataBinding
import com.taetae98.qrreader.dialog.BarcodeDialogDirections
import com.taetae98.qrreader.dto.BarcodeData
import com.taetae98.qrreader.repository.BarcodeDataRepository
import com.taetae98.qrreader.selection.BarcodeDataSelection
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentScoped
class BarcodeDataAdapter @Inject constructor() : BindingRecyclerViewAdapter<BarcodeData>(diffCallback) {
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

    var selectionTracker: BarcodeDataSelection? = null

    @Inject
    lateinit var barcodeDataRepository: BarcodeDataRepository

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BarcodeData> {
        return BarcodeDataViewHolder(
            HolderBarcodeDataBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    inner class BarcodeDataViewHolder(binding: HolderBarcodeDataBinding) : BindingViewHolder<BarcodeData, HolderBarcodeDataBinding>(binding) {
        init {
            binding.setOnClick {
                itemView.findNavController().navigate(
                    BarcodeDialogDirections.actionGlobalBarcodeDialog(item.barcode, item.format)
                )
            }
            binding.setOnBookmark {
                CoroutineScope(Dispatchers.IO).launch {
                    barcodeDataRepository.executeToggleBookmark(item.id)
                }
            }
        }

        override fun onBindViewHolder(item: BarcodeData) {
            super.onBindViewHolder(item)
            binding.isSelecting = selectionTracker?.instance?.hasSelection()
            binding.isChecked = selectionTracker?.instance?.isSelected(itemId)
        }

        override fun onBindViewDataBinding() {
            super.onBindViewDataBinding()
            binding.barcodeData = item
        }
    }
}