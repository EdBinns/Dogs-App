package com.edbinns.dogsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.edbinns.dogsapp.domain.*
import com.edbinns.dogsapp.models.Dog
import com.edbinns.dogsapp.models.Favorite
import com.edbinns.dogsapp.services.room.toFavorites
import com.edbinns.dogsapp.view.adapters.DogsAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Singleton


@HiltViewModel
class DogsViewModel @Inject constructor(
    private val getDogsImagesUseCase: GetDogsImagesUseCase,
    private val getDogsByBreedUseCase: GetDogsByBreedUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val getByUrlUseCase: GetByUrlUseCase,
    private val getResultSearchUseCase: GetResultSearchUseCase,
    private val getDogsBySubBreedUseCase: GetDogsBySubBreedUseCase,
) : ViewModel() {

    val searchingList: LiveData<List<Dog>> = getResultSearchUseCase.getSearchResult
    val imagesList = MutableLiveData<List<Dog>>()

    val isFavorite = MutableLiveData<Boolean>()


    fun getDogsImages() {
        viewModelScope.launch {
            val result = getDogsImagesUseCase()
            validateResult(result)
        }
    }


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


    fun getDogsByBreed(breed: String) {
        viewModelScope.launch {
            val breedSplit = breed.split("-")
            val result = ArrayList<Dog>()
            if (breedSplit.size <= 1) {
                result.addAll(getDogsByBreedUseCase(breedSplit[0]))
            } else {
                result.addAll(getDogsBySubBreedUseCase(breedSplit[0], breedSplit[1]))
            }
            validateResult(result)

        }
    }


    val validateResult: (List<Dog>) -> Unit = { result ->
        if (!result.isNullOrEmpty()) {
            imagesList.value = result
        }
    }

    fun isScrolling(
        manager: StaggeredGridLayoutManager,
        dogsAdapter: DogsAdapter,
        loading: Boolean
    ): Boolean {
        val visibleItems = manager.childCount
        val total = dogsAdapter.itemCount
        var firstVisibleItems: IntArray? = null
        firstVisibleItems = manager.findFirstVisibleItemPositions(firstVisibleItems)

        val pastVisibleItem =
            if (firstVisibleItems != null && firstVisibleItems.isNotEmpty()) firstVisibleItems[0] else 0

        if (!loading) {
            return ((visibleItems + pastVisibleItem) >= total)
        }
        return false
    }


}