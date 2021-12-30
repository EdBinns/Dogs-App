package com.edbinns.dogsapp.di

import android.content.Context
import androidx.room.Room
import com.edbinns.dogsapp.services.retrofit.DogFactsApiService
import com.edbinns.dogsapp.services.retrofit.DogImageApiService
import com.edbinns.dogsapp.services.room.FavoritesDB
import com.edbinns.dogsapp.utils.Constants.DOG_API_BASE_URl
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
object NetworkModule {

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
            .baseUrl(DOG_API_BASE_URl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideDogImageApiService(retrofit: Retrofit): DogImageApiService = retrofit.create(DogImageApiService::class.java)

    @Singleton
    @Provides
    fun provideDogFactsApiService(retrofit: Retrofit): DogFactsApiService = retrofit.create(DogFactsApiService::class.java)

}