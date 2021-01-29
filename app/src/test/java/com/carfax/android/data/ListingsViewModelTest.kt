package com.carfax.android.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.carfax.android.data.model.BaseListing
import com.carfax.android.data.repo.ListingsRepo
import com.carfax.android.data.viewmodel.ListingsViewModel
import com.carfax.android.util.JsonConverter
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class ListingsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    //Mock
    private inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
    private val listingRepository = mock<ListingsRepo>()
    private val listingsViewModel = ListingsViewModel(listingRepository)

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getListingList(){

        val mockListingBase:BaseListing = Gson().fromJson(
            JsonConverter.getJsonFile("listing_response.json"),
            BaseListing::class.java
        )
        val mockLiveData = MutableLiveData<BaseListing>()
        mockLiveData.value = mockListingBase

        GlobalScope.launch(Dispatchers.IO) {

            Mockito.`when`(listingsViewModel.getListingFromServerForTesting(true))
                .thenReturn( mockLiveData)
        }
    }
}