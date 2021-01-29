package com.carfax.android.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.carfax.android.data.db.ListingsDataBase
import com.carfax.android.data.model.BaseListing
import com.carfax.android.data.repo.ListingsRepo
import com.carfax.android.util.CoroutineTestRule
import com.carfax.android.util.JsonConverter
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class ListingsRepoTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
    private var listingsDataBase: ListingsDataBase = mock()


    @Test
    fun testListingData() {

        GlobalScope.launch(Dispatchers.Main){
            val mockListing = Gson().fromJson(
                JsonConverter.getJsonFile("listing_response.json"),
                BaseListing::class.java
            )
            val listingRepository = ListingsRepo(listingsDataBase,FakeListingService(mockListing))
            val listingResponse = listingRepository.getListingsStreamFromServerForTesting()
            Assert.assertNotNull(listingResponse)
        }
    }
}