package com.edbinns.dogsapp.utils

import android.view.View
import androidx.core.content.ContextCompat
import com.edbinns.dogsapp.R
import com.google.android.material.snackbar.Snackbar

enum class MessageType {
    FAILEDGETIMAGESMESSAGES,
    DEFAULTMESSAGE,
    NETWORKCONNECTIOERRORMESSAGE,
    DOWNLOADMESSAGE,
    ADDEDFAVORITEMESSAGE,
    DELETEFAVORITEMESSAGE
}

object MessageFactory {


    fun getSnackBar(type: MessageType, view: View) =
        when (type) {
            MessageType.FAILEDGETIMAGESMESSAGES -> setSnackBar(
                view,
                R.string.failed_get_images,
                R.color.error
            )
            MessageType.DEFAULTMESSAGE -> setSnackBar(view, R.string.not_found_image, R.color.error)
            MessageType.NETWORKCONNECTIOERRORMESSAGE -> setSnackBar(
                view,
                R.string.internet_problems,
                R.color.error
            )
            MessageType.DOWNLOADMESSAGE -> setSnackBar(view, R.string.download, R.color.colorGreen)
            MessageType.ADDEDFAVORITEMESSAGE -> setSnackBar(
                view,
                R.string.addFavorite,
                R.color.colorGreen
            )
            MessageType.DELETEFAVORITEMESSAGE -> setSnackBar(
                view,
                R.string.deleteFavorite,
                R.color.colorGreen
            )
        }


    private fun setSnackBar(view: View, messageID: Int, color: Int): Snackbar {

        val message = view.resources.getString(messageID)
        val sb = Snackbar.make(view, message, Snackbar.LENGTH_LONG)

        sb.view.setBackgroundColor(ContextCompat.getColor(view.context, color))
        return sb


    }
}