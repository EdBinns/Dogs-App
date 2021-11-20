package com.edbinns.dogsapp.models

import com.google.gson.annotations.SerializedName

data class DogsResponse(
    @SerializedName("message") val imageList: List<String>,
    @SerializedName("status") val status: String
)