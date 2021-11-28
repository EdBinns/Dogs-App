package com.edbinns.dogsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.edbinns.dogsapp.domain.GetDogsByBreedUseCase
import com.edbinns.dogsapp.domain.GetDogsImagesUseCase
import com.edbinns.dogsapp.models.Dog
import com.edbinns.dogsapp.view.adapters.DogsAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(
    private val getDogsImagesUseCase: GetDogsImagesUseCase,
    private val getDogsByBreedUseCase: GetDogsByBreedUseCase
): ViewModel() {

    val imagesList = MutableLiveData<List<Dog>>()
    fun getDogsImages(){
        viewModelScope.launch {
            val result = getDogsImagesUseCase()
            validateResult(result)
        }
    }

    fun getDogsByBreed(breed: String){
        viewModelScope.launch {
            val breedSplit = breed.split("-")
            val result = getDogsByBreedUseCase(breedSplit[0])
            validateResult(result)
        }
    }

    private val validateResult: (List<Dog>) -> Unit = { result->
        if(!result.isNullOrEmpty()){
            imagesList.postValue(result)
        }
    }
    fun isScrolling(manager: StaggeredGridLayoutManager, dogsAdapter: DogsAdapter, loading: Boolean): Boolean {
        val visibleItems = manager.childCount
        val total = dogsAdapter.itemCount
        var firstVisibleItems: IntArray? = null
        firstVisibleItems = manager.findFirstVisibleItemPositions(firstVisibleItems)

        val pastVisibleItem =  if (firstVisibleItems != null && firstVisibleItems.isNotEmpty()) firstVisibleItems[0] else 0

        println("total $total")
        println("visibleites $visibleItems")
        println("pastVisibleItem $pastVisibleItem")
        if (!loading) {
            return ((visibleItems + pastVisibleItem) >= total)
        }
        return false
    }
}