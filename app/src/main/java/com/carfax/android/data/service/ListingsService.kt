package com.carfax.android.data.service

import com.carfax.android.constants.CoreConstants
import com.carfax.android.data.model.BaseListing
import retrofit2.http.GET

interface ListingsService {

    @GET(CoreConstants.LISTINGS)
    suspend fun getListings() : BaseListing
}