package com.taetae98.qrreader.repository

import androidx.lifecycle.LiveData
import com.google.zxing.BarcodeFormat
import com.taetae98.qrreader.database.dao.BarcodeDataDao
import com.taetae98.qrreader.dto.BarcodeData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BarcodeDataRepository @Inject constructor(
    private val barcodeDataDao: BarcodeDataDao
) {
    fun findAllLiveData(): LiveData<List<BarcodeData>> {
        return barcodeDataDao.findAllLiveData()
    }

    fun findIsBookmarked(): LiveData<List<BarcodeData>> {
        return barcodeDataDao.findIsBookmarkedLiveData()
    }

    suspend fun findByBarcodeAndFormat(barcode: String, format: BarcodeFormat): BarcodeData? {
        return barcodeDataDao.findByBarcodeAndFormat(barcode, format)
    }

    suspend fun insert(barcode: String, format: BarcodeFormat = BarcodeFormat.QR_CODE): Long {
        val barcodeData = findByBarcodeAndFormat(barcode, format)?.copy(
            time = System.currentTimeMillis()
        ) ?: BarcodeData(
            barcode = barcode,
            format = format
        )

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