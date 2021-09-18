package com.taetae98.qrreader.repository

import androidx.lifecycle.LiveData
import com.google.zxing.BarcodeFormat
import com.taetae98.qrreader.database.dao.BarcodeDataDao
import com.taetae98.qrreader.dto.BarcodeData
import java.util.*
import javax.inject.Inject

class BarcodeDataRepository @Inject constructor(
    private val barcodeDataDao: BarcodeDataDao
) {
    fun findAllLiveData(): LiveData<List<BarcodeData>> {
        return barcodeDataDao.findAllLiveData()
    }

    fun findIsBookmarked(): LiveData<List<BarcodeData>> {
        return barcodeDataDao.findIsBookmarkedLiveData()
    }

    suspend fun findByBarcodeAndFormat(barcode: String, format: BarcodeFormat): Optional<BarcodeData> {
        return barcodeDataDao.findByBarcodeAndFormat(barcode, format)
    }

    suspend fun insert(barcode: String, format: BarcodeFormat): Long {
        val optional = findByBarcodeAndFormat(barcode, format)
        val barcodeData = if (optional.isPresent) {
            optional.get().copy(
                time = System.currentTimeMillis()
            )
        } else {
            BarcodeData(
                barcode = barcode,
                format = format
            )
        }

        return barcodeDataDao.insert(barcodeData)
    }

    suspend fun deleteByIds(collection: Collection<Long>): Int {
        return barcodeDataDao.deleteByIds(collection)
    }

    suspend fun update(barcodeData: BarcodeData): Int {
        return barcodeDataDao.update(barcodeData)
    }

    suspend fun executeToggleBookmark(id: Long): Int {
        return barcodeDataDao.executeToggleBookmark(id)
    }
}