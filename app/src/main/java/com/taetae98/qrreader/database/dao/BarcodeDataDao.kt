package com.taetae98.qrreader.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.google.zxing.BarcodeFormat
import com.taetae98.modules.library.base.BaseDao
import com.taetae98.qrreader.dto.BarcodeData
import java.util.*

@Dao
interface BarcodeDataDao : BaseDao<BarcodeData> {
    @Query("SELECT * FROM BarcodeData ORDER BY time DESC")
    fun findAllLiveData(): LiveData<List<BarcodeData>>

    @Query("SELECT * FROM BarcodeData WHERE isBookmarked = 1 ORDER BY time DESC")
    fun findIsBookmarkedLiveData(): LiveData<List<BarcodeData>>

    @Query("SELECT * FROM BarcodeData WHERE barcode=:barcode AND format=:format")
    suspend fun findByBarcodeAndFormat(barcode: String, format: BarcodeFormat): Optional<BarcodeData>

    @Query("DELETE FROM BarcodeData WHERE id IN (:collection)")
    suspend fun deleteByIds(collection: Collection<Long>): Int

    @Query("UPDATE BarcodeData SET isBookmarked = NOT isBookmarked WHERE id=:id")
    suspend fun executeToggleBookmark(id: Long): Int
}