package com.carfax.android.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.carfax.android.data.model.Listing
import com.carfax.android.data.viewmodel.ListingsViewModel
import com.carfax.android.databinding.ActivityMainBinding
import com.carfax.android.util.NetworkUtil
import com.carfax.android.util.ViewHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ListingsAdapter.ListingItemClickEvent {

    @Inject
    lateinit var listingsViewModel: ListingsViewModel

    private var binding: ActivityMainBinding? = null
    private val viewBindingMainActivity get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBindingMainActivity.root)

        Log.d("listingViewModelIns", listingsViewModel.toString())

        listingsViewModel.getListingFromServer(NetworkUtil.isConnectedToNetwork(this))
        val listingsAdapter = ListingsAdapter(this)

        viewBindingMainActivity.recyclerViewListings.adapter = listingsAdapter
        listingsViewModel.getListingsFromDb().observe(this@MainActivity) {
            listingsAdapter.setData(it)
        }

    }

    override fun onItemClicked(listing: Listing) {
        val intent = Intent(this@MainActivity, ListingDetailActivity::class.java)
        intent.putExtra("listingId", listing.id)
        startActivity(intent)
    }

    override fun onDealerCallClicked(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))

        if (ActivityCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
        ) {
            ViewHelper.requestPhonePermission(this@MainActivity)
            return
        }
        startActivity(callIntent)
    }

}