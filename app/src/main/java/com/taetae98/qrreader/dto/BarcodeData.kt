package com.taetae98.qrreader.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.zxing.BarcodeFormat

@Entity
data class BarcodeData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val time: Long = System.currentTimeMillis(),
    val barcode: String = "",
    val format: BarcodeFormat = BarcodeFormat.QR_CODE,
    val name: String = "",
    val isBookmarked: Boolean = false
)