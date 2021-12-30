package com.edbinns.dogsapp.viewmodel

import androidx.lifecycle.*
import com.edbinns.dogsapp.models.Dog
import com.edbinns.dogsapp.models.Favorite
import com.edbinns.dogsapp.services.room.toFavorites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import com.edbinns.dogsapp.domain.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getALLFavoriteUseCase: GetALLFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getByUrlUseCase: GetByUrlUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase
) : ViewModel(){

    val favoritesList : LiveData<List<Favorite>> = getALLFavoriteUseCase.getAllFavorite
    val mutableList : MutableLiveData<List<Favorite>> = MutableLiveData<List<Favorite>>()


    fun getAllFavorites(){
        mutableList.postValue(favoritesList.value)
    }

}