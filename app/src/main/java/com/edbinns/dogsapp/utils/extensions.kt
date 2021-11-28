package com.edbinns.dogsapp.utils

import com.edbinns.dogsapp.models.Dog

fun List<String>.toDogsList() : List<Dog> {
    return map { url->
        val breed = url.split("/")[4]
        Dog(url,breed)
    }
}

fun Dog.splitBreed(): String {
    val splitBreed = this.breed.split("-")
    return if (splitBreed.size > 1) "${splitBreed[0]} ${splitBreed[1]}" else splitBreed[0]
}