package com.carfax.android.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.carfax.android.R

object ViewHelper {

    fun ImageView.loadImage(imageUrl: String) {
        Glide.with(this.context)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(R.drawable.car_fax_img)
            .into(this)
    }

    fun requestPhonePermission(context: Context) {
        ActivityCompat.requestPermissions(
            scanForActivity(context)!!,
            arrayOf(Manifest.permission.CALL_PHONE),
            100)
    }

    private fun scanForActivity(cont: Context?): Activity? {
        return when (cont) {
            null -> null
            is Activity -> cont
            is ContextWrapper -> scanForActivity(cont.baseContext)
            else -> null
        }

    }

}