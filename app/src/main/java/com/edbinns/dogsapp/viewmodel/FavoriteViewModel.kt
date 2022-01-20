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
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val getByUrlUseCase: GetByUrlUseCase,
) : ViewModel(){

    val favoritesList : LiveData<List<Favorite>> = getALLFavoriteUseCase.getAllFavorite
    val mutableList : MutableLiveData<List<Favorite>> = MutableLiveData<List<Favorite>>()


    val isFavorite = MutableLiveData<Boolean>()

    fun addFavorite(data: Dog) {
        val favorite = data.toFavorites()
        println("favorite $favorite")
        viewModelScope.launch(Dispatchers.IO) {
            val temp: Favorite? = getByUrlUseCase(favorite.imageURL)
            if (temp == null) {
                addFavoriteUseCase(favorite)
                isFavorite.postValue(true)
            }

        }
    }

    fun deleteFavorite(data: Dog) {
        viewModelScope.launch(Dispatchers.IO) {
            val temp: Favorite? = getByUrlUseCase(data.imageURL)
            temp?.let {
                deleteFavoriteUseCase(temp)
                isFavorite.postValue(false)
            }
        }
    }

    fun validateFavorite(data: Dog) {
        viewModelScope.launch(Dispatchers.IO) {
            val temp: Favorite? = getByUrlUseCase(data.imageURL)
            temp?.let {
                isFavorite.postValue(true)
            }
        }
    }
    fun getAllFavorites(){
        mutableList.postValue(favoritesList.value)
    }

}