package com.edbinns.dogsapp.di

import android.content.Context
import androidx.room.Room
import com.edbinns.dogsapp.services.retrofit.ApiService
import com.edbinns.dogsapp.services.room.FavoritesDB
import com.edbinns.dogsapp.utils.Constants.API_BASE_URl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        FavoritesDB::class.java,
        "favoriteDB"
    ).build()

    @Singleton
    @Provides
    fun provideDao(db: FavoritesDB) = db.favoritesDao()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient
            .Builder()
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(API_BASE_URl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
    

}