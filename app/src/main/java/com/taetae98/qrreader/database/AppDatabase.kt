package com.taetae98.qrreader.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.taetae98.qrreader.application.DATABASE_NAME
import com.taetae98.qrreader.database.converter.BarcodeDataTypeConverter
import com.taetae98.qrreader.database.dao.BarcodeDataDao
import com.taetae98.qrreader.dto.BarcodeData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        BarcodeData::class
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(BarcodeDataTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .addCallback(
                        object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                CoroutineScope(Dispatchers.IO).launch {
                                    getInstance(context).barcodeDataDao().insert(
                                        BarcodeData(
                                            barcode = "https://play.google.com/store/apps/details?id=com.taetae98.qrreader",
                                            name = "Application",
                                            isBookmarked = true
                                        )
                                    )
                                }
                            }
                        }
                    )
                    .build()
                    .also {
                        instance = it
                    }
            }
        }
    }

    abstract fun barcodeDataDao(): BarcodeDataDao
}