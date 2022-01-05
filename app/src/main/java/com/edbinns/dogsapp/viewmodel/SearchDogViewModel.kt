package com.edbinns.dogsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edbinns.dogsapp.domain.GetAllBreedsUseCase
import com.edbinns.dogsapp.domain.GetDogsByBreedUseCase
import com.edbinns.dogsapp.domain.GetDogsBySubBreedUseCase
import com.edbinns.dogsapp.domain.SetSearchResultUseCase
import com.edbinns.dogsapp.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class SearchDogViewModel @Inject constructor(
    private val getDogsByBreedUseCase: GetDogsByBreedUseCase,
    private val getAllBreedsUseCase: GetAllBreedsUseCase,
    private val getDogsBySubBreedUseCase: GetDogsBySubBreedUseCase,
    private val setSearchResultUseCase: SetSearchResultUseCase
) : ViewModel() {

    val allBreeds = MutableLiveData<List<String>>()
    val allSubBreeds = MutableLiveData<List<String>>()
    val isSet = MutableLiveData<Boolean>()


    init {
        getAllBreeds()
    }

    private lateinit var allBreedsJSON: JSONObject

    private fun getBreeds(data: JSONObject) {
        val list: ArrayList<String> = ArrayList()
        data.keys().forEach { list.add(it) }
        allBreeds.postValue(list)
    }


    fun getSubBreed(breed: String) {
        val jsonArray = allBreedsJSON[breed] as JSONArray
        val jsonLenght = jsonArray.length()
        val list = ArrayList<String>()
        if (jsonLenght > 0) {
            for (i in 0 until jsonLenght){
                val subBreed = jsonArray.getString(i)
                list.add(subBreed)
            }
        }
        allSubBreeds.postValue(list)
    }

    private fun getAllBreeds() {
        viewModelScope.launch(Dispatchers.IO) {
            allBreedsJSON = getAllBreedsUseCase()
            getBreeds(allBreedsJSON)
        }
    }

    fun search(breed: String, subBreed: String) {
        if (subBreed.isEmpty())
            getDogsByBreed(breed)
        else
            getDogsBySubBreed(breed, subBreed)
    }

    private fun getDogsByBreed(breed: String) {
        viewModelScope.launch {
            val breedSplit = breed.split("-")
            val result = getDogsByBreedUseCase(breedSplit[0])
            validateResult(result)
        }
    }

    private fun getDogsBySubBreed(breed: String, subBreed: String) {
        viewModelScope.launch {
            val result = getDogsBySubBreedUseCase(breed, subBreed)
            validateResult(result)
        }
    }

    private val validateResult: (List<Dog>) -> Unit = { result ->
        if (!result.isNullOrEmpty()) {
            isSet.postValue(setSearchResultUseCase(result))
        }
    }

}