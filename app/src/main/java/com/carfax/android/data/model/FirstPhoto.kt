package com.carfax.android.data.model

import com.google.gson.annotations.SerializedName

data class FirstPhoto(
    @SerializedName("medium")
    var mediumImage: String? = ""
)