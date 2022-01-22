package com.edbinns.dogsapp.domain

import androidx.lifecycle.LiveData
import com.edbinns.dogsapp.models.Dog
import com.edbinns.dogsapp.models.Favorite
import com.edbinns.dogsapp.services.provider.ResultSearchProvider
import com.edbinns.dogsapp.services.repository.FavoriteRepository
import javax.inject.Inject

class GetResultSearchUseCase @Inject constructor(private val provider: ResultSearchProvider) {

    val getSearchResult: LiveData<List<Dog>> =  provider.resultSearch

}