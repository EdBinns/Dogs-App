package com.edbinns.dogsapp.services.room

import com.edbinns.dogsapp.models.Dog
import com.edbinns.dogsapp.models.Favorite

fun List<Favorite>.toDogsList() = map(Favorite::toDog)

fun Dog.toFavorites() = Favorite(
    0,
    imageURL,
    breed
)

fun Favorite.toDog() = Dog(
   imageURL,
    breed
)