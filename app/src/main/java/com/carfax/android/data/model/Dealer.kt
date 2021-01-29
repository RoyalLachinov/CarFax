package com.carfax.android.data.model

import com.google.gson.annotations.SerializedName

data class Dealer(
    @SerializedName("phone")
    var phone: String = ""
)
