package com.edbinns.dogsapp.services.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.edbinns.dogsapp.models.Favorite
import com.edbinns.dogsapp.utils.MessageType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorProvider  @Inject constructor(){

    val getMessageError: MutableLiveData<MessageType> = MutableLiveData()
}