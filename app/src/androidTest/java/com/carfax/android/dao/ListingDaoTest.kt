package com.carfax.android.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.carfax.android.data.dao.ListingsDao
import com.carfax.android.data.db.ListingsDataBase
import com.carfax.android.data.model.BaseListing
import com.carfax.android.util.JsonUtil
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

/**
 * In this class I'm testing insertion and fetching listings with fake data that using
 * from {listing_response.json} file
 */

@RunWith(AndroidJUnit4::class)
class ListingDaoTest {

    private lateinit var listingsDataBase: ListingsDataBase
    private lateinit var listingsDao: ListingsDao

    @Before
    @Throws(Exception::class)
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        listingsDataBase = Room.inMemoryDatabaseBuilder(
            context,ListingsDataBase::class.java
        ).build()
        listingsDao = listingsDataBase.getListingDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        listingsDataBase.close()
    }

    @Test
    @Throws(Exception::class)
    fun testListings() = runBlocking {
        val string = JsonUtil.convertStreamToString(
            InstrumentationRegistry.getInstrumentation().context.resources.assets
                .open("listing_response.json")
        )
        val baseListing = Gson().fromJson(
            string,
            BaseListing::class.java
        )
        val firstList = baseListing.listing[0]

        Assert.assertEquals(firstList.model, "124 Spider")

        listingsDao.insertListings(baseListing.listing)

        val listingsFromDb = listingsDao.getListingsForTesting()
        Assert.assertEquals(listingsFromDb.size, baseListing.listing.size)

        val selectedListing = listingsDataBase.getListingDao()
            .getListingByIdForTesting("JC1NFAEK3H0108772ZVF6RAG00120201031")
        Assert.assertEquals(firstList.dealer, selectedListing.dealer)
    }

}