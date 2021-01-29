package com.carfax.android.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carfax.android.data.dao.ListingsDao
import com.carfax.android.data.model.Listing

@Database(
    entities = [Listing::class],
    version = 1,
    exportSchema = false
)
abstract class ListingsDataBase : RoomDatabase() {
    abstract fun getListingDao(): ListingsDao
}