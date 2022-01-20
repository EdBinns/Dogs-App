package com.edbinns.dogsapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.edbinns.dogsapp.domain.*
import com.edbinns.dogsapp.models.Dog
import com.edbinns.dogsapp.models.Favorite
import com.edbinns.dogsapp.services.room.toFavorites
import com.edbinns.dogsapp.utils.MessageType
import com.edbinns.dogsapp.utils.NetWorkConnectivity
import com.edbinns.dogsapp.utils.NotificationUtils
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
    private val getResultSearchUseCase: GetResultSearchUseCase,
    private val getDogsBySubBreedUseCase: GetDogsBySubBreedUseCase,
    private val getErrorUseCase: GetErrorUseCase,

) : ViewModel() {

    val searchingList: LiveData<List<Dog>> = getResultSearchUseCase.getSearchResult
    val imagesList = MutableLiveData<List<Dog>>()
    val errorMessage: LiveData<MessageType> = getErrorUseCase.getErrorMessage


    fun getDogsImages() {
        viewModelScope.launch {
            val result = getDogsImagesUseCase()
            validateResult(result)
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