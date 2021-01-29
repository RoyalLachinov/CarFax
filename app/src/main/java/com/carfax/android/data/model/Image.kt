package com.carfax.android.data.model

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("firstPhoto")
    var firstPhoto: FirstPhoto
)