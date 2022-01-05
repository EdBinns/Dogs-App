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


fun <T> T?.toNonNullable() : T {
    if(this == null) {
        throw KotlinNullPointerException()
    }
    return this as T // this would actually get smart cast, but this
    // explicit cast demonstrates the point better
}