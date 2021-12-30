package com.edbinns.dogsapp.models

data class DogFactsResponse(
    val facts: List<String>,
    val success: Boolean
)