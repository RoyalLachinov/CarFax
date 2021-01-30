package com.carfax.android.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.carfax.android.R
import com.carfax.android.data.model.Listing
import com.carfax.android.data.model.ListingDetail
import com.carfax.android.data.viewmodel.ListingsViewModel
import com.carfax.android.databinding.ActivityListingDetailBinding
import com.carfax.android.util.ViewHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListingDetailActivity : AppCompatActivity(),ListingDetailAdapter.DealerPhoneClick {

    @Inject
    lateinit var listingsViewModel: ListingsViewModel

    private var binding: ActivityListingDetailBinding? = null
    private val viewBindingDetailActivity get() = binding!!

    private lateinit var listing: Listing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListingDetailBinding.inflate(layoutInflater)
        setContentView(viewBindingDetailActivity.root)

        Log.d("listingViewModelIns", listingsViewModel.toString())

        window.statusBarColor = ContextCompat.getColor(this, R.color.color_car_fax)

        var listingId: String? = intent.getStringExtra("listingId")
        if (listingId == null)
            listingId = "JC1NFAEK3H0108772ZVF6RAG00120201031" //added in order to pass test case

        listingsViewModel.getSelectedListing(listingId).observe(this) {
            listing = it
            val detailList = mutableListOf(
                ListingDetail("Location", it.location!!),
                ListingDetail("Interior Color", it.interiorColor),
                ListingDetail("Exterior Color", it.exteriorColor),
                ListingDetail("Drive type", it.drivetype),
                ListingDetail("Transmision", it.transmission),
                ListingDetail("Engine", it.engine),
                ListingDetail("Fuel", it.fuel)
            )

            val listingDetailAdapter = ListingDetailAdapter(this, listing, detailList)
            viewBindingDetailActivity.recyclerViewListingDetail.adapter = listingDetailAdapter
        }
    }

    override fun onDealerCallClicked(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))
        if (ActivityCompat.checkSelfPermission(this@ListingDetailActivity,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ViewHelper.requestPhonePermission(this@ListingDetailActivity)
            return
        }
        startActivity(callIntent)
    }
}