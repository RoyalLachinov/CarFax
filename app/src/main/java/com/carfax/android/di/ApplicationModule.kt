package com.carfax.android.di

import android.content.Context
import androidx.room.Room
import com.carfax.android.constants.CoreConstants
import com.carfax.android.data.db.ListingsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): ListingsDataBase {
        return Room.databaseBuilder(
            appContext,
            ListingsDataBase::class.java,
            CoreConstants.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun getRetrofitInstance(): Retrofit {

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(CoreConstants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}