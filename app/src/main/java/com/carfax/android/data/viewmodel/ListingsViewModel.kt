package com.carfax.android.data.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carfax.android.data.model.BaseListing
import com.carfax.android.data.model.Listing
import com.carfax.android.data.repo.ListingsRepo

class ListingsViewModel @ViewModelInject constructor(private val listingsRepo: ListingsRepo) :
    ViewModel() {


    fun getListingFromServer(isNetworkConnected: Boolean) {
        if (isNetworkConnected)
            listingsRepo.getListingsStreamFromServer()
    }

    fun getListingFromServerForTesting(isNetworkConnected: Boolean): LiveData<BaseListing> {
        return if (isNetworkConnected)
            listingsRepo.getListingsStreamFromServerForTesting()
        else MutableLiveData()
    }

    fun getListingsFromDb(): LiveData<MutableList<Listing>> =
        listingsRepo.getListingsFromDb()

    fun getSelectedListing(listingId: String): LiveData<Listing> {
        return listingsRepo.getListingById(listingId)
    }

    /**
     * Cancel the job when view destroyed.
     */
    public override fun onCleared() {
        super.onCleared()
        listingsRepo.clearRepositoryJob()
    }
}