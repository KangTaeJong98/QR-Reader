package com.taetae98.qrreader.database.converter

import androidx.room.TypeConverter
import com.google.zxing.BarcodeFormat

class BarcodeDataTypeConverter {
    @TypeConverter
    fun formatToString(format: BarcodeFormat): String {
        return format.name
    }

    @TypeConverter
    fun stringToFormat(string: String): BarcodeFormat {
        return BarcodeFormat.valueOf(string)
    }
}