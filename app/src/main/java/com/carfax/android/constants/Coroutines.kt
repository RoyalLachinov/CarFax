package com.carfax.android.constants

import kotlinx.coroutines.*

object Coroutines {

    fun <T : Any> ioThenMain(work: suspend (() -> T?), callback: ((T?) -> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async rt@{
                return@rt work()
            }.await()
            callback(data)
        }

    fun <T : Any> ioThenIO(work: suspend (() -> T?), callback: suspend ((T?) -> Unit)) =
        CoroutineScope(Dispatchers.IO).launch {
            val data = CoroutineScope(Dispatchers.IO).async rt@{
                return@rt work()
            }.await()
            callback(data)
        }
}