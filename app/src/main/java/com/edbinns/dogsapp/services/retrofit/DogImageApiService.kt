package com.edbinns.dogsapp.services.retrofit

import com.edbinns.dogsapp.models.DogsResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogImageApiService {

    @GET("breeds/image/random/{number}")
    suspend fun getDogImageList(@Path("number") number: String) : Response<DogsResponse>

    @GET("breed/{breedName}/images/random/{number}")
    suspend fun getDogsImagesByBreed(
        @Path("number") number: String,
        @Path("breedName") breedName: String
    ): Response<DogsResponse>

    @GET("breed/{breedName}/{subbreed}/images/random/{number}")
    suspend fun getDogsImagesBySubBreed(
        @Path("number") number: String,
        @Path("breedName") breedName: String,
        @Path("subbreed") subBreed: String,
    ): Response<DogsResponse>

    @GET("breeds/list/all")
    suspend fun getDogsBreed():Response<ResponseBody>

}