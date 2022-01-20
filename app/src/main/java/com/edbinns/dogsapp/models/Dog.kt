package com.edbinns.dogsapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Dog(val imageURL: String, val breed: String) : Serializable
