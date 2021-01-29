package com.carfax.android.di

import com.carfax.android.data.db.ListingsDataBase
import com.carfax.android.data.repo.ListingsRepo
import com.carfax.android.data.service.ListingsService
import com.carfax.android.data.viewmodel.ListingsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit

@InstallIn(ActivityRetainedComponent::class)
@Module
object ViewModelModule {

    @Provides
    @ActivityRetainedScoped
    fun provideStoryApiService(retrofit: Retrofit): ListingsService {
        return retrofit.create(ListingsService::class.java)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideStoryRepo(storiesDatabase: ListingsDataBase,
                         storiesApiService: ListingsService)
            : ListingsRepo {
        return ListingsRepo(storiesDatabase,storiesApiService)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideStoryViewModel(storiesRepo: ListingsRepo):ListingsViewModel{
        return ListingsViewModel(storiesRepo)
    }
}