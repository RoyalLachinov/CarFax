package com.carfax.android.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.carfax.android.data.model.Listing
import com.carfax.android.databinding.ItemListingsBinding
import com.carfax.android.util.DiffUtilCallback

class ListingsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var clickListenerListing: ListingItemClickEvent
    interface ListingItemClickEvent {
        fun onItemClicked(listing: Listing)
        fun onDealerCallClicked(phoneNumber:String)
    }

    private var listings: MutableList<Listing> = mutableListOf()
    fun setData(newList: MutableList<Listing>) {
        val diffCallback = DiffUtilCallback(listings, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listings.clear()
        listings.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemListingsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val listing = listings[position]
        val binding = (holder as ViewHolder).binding

        listing.let {
            binding.listing = it
        }

        binding.parentListingCardLayout.setOnClickListener {
            clickListenerListing.onItemClicked(listing)
        }

        binding.tvCallDealer.setOnClickListener {
            clickListenerListing.onDealerCallClicked(listing.dealer?.phone!!)
        }
    }

    override fun getItemCount(): Int = listings.size

    class ViewHolder(val binding: ItemListingsBinding) :
        RecyclerView.ViewHolder(binding.root)
}