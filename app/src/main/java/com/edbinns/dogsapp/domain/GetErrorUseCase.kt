package com.edbinns.dogsapp.domain

import androidx.lifecycle.LiveData
import com.edbinns.dogsapp.models.Favorite
import com.edbinns.dogsapp.services.provider.ErrorProvider
import com.edbinns.dogsapp.services.repository.FavoriteRepository
import com.edbinns.dogsapp.utils.MessageType
import javax.inject.Inject

class GetErrorUseCase @Inject constructor(private val provider: ErrorProvider) {

    val getErrorMessage: LiveData<MessageType> =  provider.getMessageError

}