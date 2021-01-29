package com.carfax.android.data

import com.carfax.android.data.model.BaseListing
import com.carfax.android.data.service.ListingsService

/**
 * This class is a fake api service for getting fake listings list in repository test class
 */

class FakeListingService (private var response: Any) : ListingsService {

    override suspend fun getListings(): BaseListing {
        return response as BaseListing
    }
}