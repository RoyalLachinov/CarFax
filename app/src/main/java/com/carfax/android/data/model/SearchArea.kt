package com.carfax.android.data.model

import com.google.gson.annotations.SerializedName

data class SearchArea(
    @SerializedName("city")
    var city: String = "",
    @SerializedName("state")
    var state: String = ""
)
