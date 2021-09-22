package com.taetae98.qrreader.dto

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.zxing.BarcodeFormat
import java.io.Serializable

@Entity(
    indices = [
        Index(
            value = ["barcode", "format"],
            name = "barcode_and_format_index",
            unique = true
        ),
        Index(
            value = ["isBookmarked"],
            name = "is_bookmarked_index"
        ),
        Index(
            value = ["name"],
            name = "name_index"
        )
    ]
)
data class BarcodeData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val barcode: String = "",
    val format: BarcodeFormat = BarcodeFormat.QR_CODE,
    val time: Long = System.currentTimeMillis(),
    val name: String = "",
    val isBookmarked: Boolean = false
) : Serializable