package com.carfax.android.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carfax.android.data.model.Listing
import com.carfax.android.data.model.ListingDetail
import com.carfax.android.databinding.ItemListingDetailBinding
import com.carfax.android.databinding.ItemListingDetailFooterBinding
import com.carfax.android.databinding.ItemListingDetailHeaderBinding
import com.carfax.android.util.ViewHelper.loadImage

class ListingDetailAdapter(
    private var listing: Listing,
    private var listings: MutableList<ListingDetail>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var dealerPhoneClick: DealerPhoneClick
    interface DealerPhoneClick {
        fun onDealerCallClicked(phoneNumber: String)
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
        private const val TYPE_FOOTER = 2
    }

    @Throws(RuntimeException::class)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_HEADER -> {
                return HeaderViewHolder(
                    ItemListingDetailHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TYPE_FOOTER -> {
                return FooterViewHolder(
                    ItemListingDetailFooterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TYPE_ITEM -> {
                return ItemViewHolder(
                    ItemListingDetailBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw RuntimeException("There is no type that matches the type $viewType")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val binding = holder.binding
                listing.let {
                    binding.listing = it
                    binding.imageViewCar.loadImage(it.imageUrl!!)
                }
            }
            is ItemViewHolder -> {
                val listing = listings[position - 1]
                val binding = (holder).binding
                listing.let {
                    binding.listingDetail = it
                }
            }
            is FooterViewHolder -> {
                val binding = (holder).binding
                binding.tvCallDealer.setOnClickListener {
                    dealerPhoneClick.onDealerCallClicked(listing.dealer!!.phone)
                }
            }
        }

    }

    override fun getItemCount(): Int = listings.size + 2

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            listings.size + 1 -> TYPE_FOOTER
            else -> TYPE_ITEM
        }
    }

    class ItemViewHolder(val binding: ItemListingDetailBinding) :
        RecyclerView.ViewHolder(binding.root)

    class HeaderViewHolder(val binding: ItemListingDetailHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    class FooterViewHolder(val binding: ItemListingDetailFooterBinding) :
        RecyclerView.ViewHolder(binding.root)

}