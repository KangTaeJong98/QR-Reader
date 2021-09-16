package com.taetae98.qrreader.repository

import androidx.lifecycle.LiveData
import com.taetae98.qrreader.database.dao.BarcodeDataDao
import com.taetae98.qrreader.dto.BarcodeData
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

    suspend fun insert(barcodeData: BarcodeData): Long {
        return barcodeDataDao.insert(barcodeData)
    }

    suspend fun delete(barcodeData: BarcodeData): Int {
        return barcodeDataDao.delete(barcodeData)
    }

    suspend fun update(barcodeData: BarcodeData): Int {
        return barcodeDataDao.update(barcodeData)
    }
}