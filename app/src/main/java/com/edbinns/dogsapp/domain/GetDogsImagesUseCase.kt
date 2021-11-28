package com.edbinns.dogsapp.domain

import com.edbinns.dogsapp.services.repository.DogsRepository
import javax.inject.Inject

class GetDogsImagesUseCase@Inject constructor(private val repository : DogsRepository) {
    suspend operator fun invoke() = repository.getDogsImages()

}