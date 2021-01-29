package com.carfax.android.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "listings")
data class Listing constructor(
    @PrimaryKey
    @SerializedName("id")
    var id: String = "",
    @SerializedName("year")
    var year: String = "",
    @SerializedName("make")
    var make: String = "",
    @SerializedName("model")
    var model: String = "",
    @SerializedName("trim")
    var trim: String = "",
    @SerializedName("mileage")
    var mileage: String = "",
    @SerializedName("listPrice")
    var price: String = "",
    @SerializedName("interiorColor")
    var interiorColor: String = "",
    @SerializedName("exteriorColor")
    var exteriorColor: String = "",
    @SerializedName("drivetype")
    var drivetype: String = "",
    @SerializedName("transmission")
    var transmission: String = "",
    @SerializedName("engine")
    var engine: String = "",
    @SerializedName("bodytype")
    var bodytype: String = "",
    @SerializedName("fuel")
    var fuel:String = "",

    var updatedAt: String = "2020-02-10 11:38:22",
    var location: String? = "",
    var imageUrl: String? = "",

    @SerializedName("images")
    @Ignore
    var image: Image? = null,
    @SerializedName("dealer")
    @Embedded
    var dealer: Dealer? = null
) {
    constructor() : this(
        "", "", "", "", "", "",
        "", "", "", "", "",
        "", "", "", "", "", null, null
    )

}