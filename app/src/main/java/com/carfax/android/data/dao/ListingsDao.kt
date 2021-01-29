package com.carfax.android.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carfax.android.data.model.Listing

@Dao
interface ListingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListings(stories: MutableList<Listing>)

    @Query("SELECT * FROM listings")
    fun getListings(): LiveData<MutableList<Listing>>

    @Query("SELECT * FROM listings")
    fun getListingsForTesting(): MutableList<Listing>

    @Query("SELECT * FROM listings where id = :listingId")
    fun getListingById(listingId:String): LiveData<Listing>

    @Query("SELECT * FROM listings where id = :listingId")
    fun getListingByIdForTesting(listingId:String): Listing

}