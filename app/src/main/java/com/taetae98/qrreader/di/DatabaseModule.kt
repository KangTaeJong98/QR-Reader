package com.taetae98.qrreader.di

import android.content.Context
import com.taetae98.qrreader.database.AppDatabase
import com.taetae98.qrreader.database.dao.BarcodeDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun providesBarcodeDataDao(
        appDatabase: AppDatabase
    ): BarcodeDataDao {
        return appDatabase.barcodeDataDao()
    }
}