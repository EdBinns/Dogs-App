package com.edbinns.dogsapp.domain

import com.edbinns.dogsapp.models.Dog
import com.edbinns.dogsapp.services.provider.ResultSearchProvider
import javax.inject.Inject

class SetSearchResultUseCase @Inject constructor(private val provider: ResultSearchProvider) {

     operator fun invoke(result: List<Dog>): Boolean = provider.setResult(result)
}