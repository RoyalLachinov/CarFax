package com.carfax.android.data.model

import com.google.gson.annotations.SerializedName

data class BaseListing(
    @SerializedName("listings")
    var listing: MutableList<Listing>,
    @SerializedName("searchArea")
    var searchArea: SearchArea
)
