package com.taetae98.qrreader.selection

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView

class BarcodeDataSelection(private val recyclerView: RecyclerView) {
    val instance by lazy {
        SelectionTracker.Builder(
            "com.taetae98.qrreader.selection",
            recyclerView,
            BarcodeDataKeyProvider(recyclerView),
            BarcodeDataItemDetailLookup(recyclerView),
            StorageStrategy.createLongStorage()
        ).build()
    }

    class BarcodeDataKeyProvider(private val recyclerView: RecyclerView) : ItemKeyProvider<Long>(SCOPE_MAPPED) {
        override fun getKey(position: Int): Long? {
            return recyclerView.findViewHolderForAdapterPosition(position)?.itemId
        }

        override fun getPosition(key: Long): Int {
            return recyclerView.findViewHolderForItemId(key).bindingAdapterPosition
        }
    }

    class BarcodeDataItemDetailLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val child = recyclerView.findChildViewUnder(e.x, e.y) ?: return null
            val holder = recyclerView.getChildViewHolder(child) ?: return null

            return object : ItemDetails<Long>() {
                override fun getPosition(): Int {
                    return holder.bindingAdapterPosition
                }

                override fun getSelectionKey(): Long {
                    return holder.itemId
                }
            }
        }
    }
}