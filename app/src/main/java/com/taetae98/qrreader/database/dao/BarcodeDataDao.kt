package com.taetae98.qrreader.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.taetae98.modules.library.base.BaseDao
import com.taetae98.qrreader.dto.BarcodeData

@Dao
interface BarcodeDataDao : BaseDao<BarcodeData> {
    @Query("SELECT * FROM BarcodeData ORDER BY time DESC")
    fun findAllLiveData(): LiveData<List<BarcodeData>>

    @Query("SELECT * FROM BarcodeData WHERE isBookmarked = 1")
    fun findIsBookmarkedLiveData(): LiveData<List<BarcodeData>>
}