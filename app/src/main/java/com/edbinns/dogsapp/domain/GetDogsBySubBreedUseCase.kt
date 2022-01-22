package com.edbinns.dogsapp.domain

import com.edbinns.dogsapp.services.repository.DogsRepository
import javax.inject.Inject

class GetDogsBySubBreedUseCase @Inject constructor(private val repository : DogsRepository) {
    suspend operator fun invoke(breed : String, subBreed : String) = repository.getDogsImagesBySubBreed(breed,subBreed)
}