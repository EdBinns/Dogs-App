package com.edbinns.dogsapp.services.retrofit

import com.edbinns.dogsapp.models.DogsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("breeds/image/random/{number}")
    suspend fun getDogImageList(@Path("number") number: String) : Response<DogsResponse>

    @GET("breed/{breedName}/images/random/{number}")
    suspend fun getDogsImagesByBreed(@Path("number") number: String,@Path("breedName") breedName: String): Response<DogsResponse>


}