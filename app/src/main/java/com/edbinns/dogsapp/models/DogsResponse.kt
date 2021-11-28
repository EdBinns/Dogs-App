package com.edbinns.dogsapp.models

import com.google.gson.annotations.SerializedName

data class DogsResponse(
    @SerializedName("message") var imageList: List<String>,
    @SerializedName("status") var status: String
)