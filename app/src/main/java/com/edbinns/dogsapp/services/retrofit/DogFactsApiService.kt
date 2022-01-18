package com.edbinns.dogsapp.services.retrofit

import com.edbinns.dogsapp.models.FactsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface DogFactsApiService {


    @GET("dogs")
    suspend fun getDogsFactsFromService(@Url url: String?, @Query("number") number : String):  Response<FactsResponse>
}