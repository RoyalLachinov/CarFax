package com.carfax.android.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.carfax.android.constants.Coroutines
import com.carfax.android.data.db.ListingsDataBase
import com.carfax.android.data.model.BaseListing
import com.carfax.android.data.model.Listing
import com.carfax.android.data.service.ListingsService
import kotlinx.coroutines.Job
import javax.inject.Inject

class ListingsRepo @Inject constructor(
    private val listingDatabase: ListingsDataBase,
    private val listingApiService: ListingsService
) {
    private lateinit var listingRepositoryJob: Job

    fun getListingsStreamFromServer() {

        listingRepositoryJob = Coroutines.ioThenIO(
            {
                listingApiService.getListings()
            },
            {
                it?.listing?.forEach { listing ->
                    listing.location = it.searchArea.city + ", " + it.searchArea.state
                    listing.imageUrl = listing.image?.firstPhoto?.mediumImage!!
                }
                listingDatabase.getListingDao().insertListings(it?.listing!!)
            }
        )
    }

    fun getListingsStreamFromServerForTesting(): LiveData<BaseListing> {
        val listingMutableLiveData = MutableLiveData<BaseListing>()
        listingRepositoryJob = Coroutines.ioThenIO(
            {
                listingApiService.getListings()
            },
            {
                it?.listing?.forEach { listing ->
                    listing.location = it.searchArea.city + ", " + it.searchArea.state
                    listing.imageUrl = listing.image?.firstPhoto?.mediumImage!!
                }
                listingDatabase.getListingDao().insertListings(it?.listing!!)
                listingMutableLiveData.value = it
            }
        )

        return listingMutableLiveData
    }

    fun getListingsFromDb(): LiveData<MutableList<Listing>> {
        return listingDatabase.getListingDao().getListings()
    }

    fun getListingById(listingId:String): LiveData<Listing> {
        return listingDatabase.getListingDao().getListingById(listingId)
    }


    fun clearRepositoryJob() {
        if (::listingRepositoryJob.isInitialized) listingRepositoryJob.cancel()
    }
}