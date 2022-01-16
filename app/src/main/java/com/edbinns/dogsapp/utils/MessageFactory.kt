package com.edbinns.dogsapp.utils

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.edbinns.dogsapp.R
import com.google.android.material.snackbar.Snackbar

enum class MessageType{
    FAILEDGETIMAGESMESSAGES,
    DEFAULTMESSAGE,

}
object MessageFactory {

    @RequiresApi(Build.VERSION_CODES.M)
    fun getSnackBar(type: MessageType, view: View) =
        when(type){
            MessageType.FAILEDGETIMAGESMESSAGES -> setSnackBar(view,R.string.failed_get_images, R.color.error)
            MessageType.DEFAULTMESSAGE -> setSnackBar(view,R.string.not_found_image, R.color.error)
        }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun setSnackBar(view: View, messageID: Int, color: Int): Snackbar {
        val message = view.resources.getString(messageID)
        val sb = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        view.resources?.getColor(color, null)?.let { sb.view.setBackgroundColor(it) }
        return sb
    }
}