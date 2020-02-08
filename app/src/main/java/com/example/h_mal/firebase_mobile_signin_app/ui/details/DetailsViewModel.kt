package com.example.h_mal.firebase_mobile_signin_app.ui.details

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.h_mal.firebase_mobile_signin_app.data.network.model.User
import com.example.h_mal.firebase_mobile_signin_app.data.repository.Repository
import com.example.h_mal.firebase_mobile_signin_app.ui.CompletionListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException


class DetailsViewModel(
    private val repository: Repository
) : ViewModel() {

    var completionListener : CompletionListener? = null

    val currentUser = MutableLiveData<User>()

    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var imageB64String: String? = null

    fun onUpdateButtonClick(view: View){
        completionListener?.onStarted()
        if(firstName.isNullOrEmpty() || lastName.isNullOrEmpty() || email.isNullOrEmpty()){
            completionListener?.onFailure("names or email field empty")
            return
        }

        if (imageB64String.isNullOrEmpty()){
            completionListener?.onFailure("image is invalid")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try{
                val uid = repository.getUid()
                val user = User(uid, firstName!!, lastName!!, email!!, imageB64String!!)

                val response = repository.updateUser(user)

                response.user?.let {
                    currentUser.value = it
                    completionListener?.onSuccess()
                    return@launch
                }
                completionListener?.onFailure("failed to update user")
            }catch (e : Exception){
                completionListener?.onFailure(e.message!!)
            }

        }
    }

    fun logOutUser() = repository.logoutUser()

    fun initialise(){
        completionListener?.onStarted()
        CoroutineScope(Dispatchers.Main).launch {
            try{
                val uid = repository.getUid()
                val responseObject = repository.getUser(uid)
                responseObject.user?.let {
                    imageB64String = it.avatar

                    currentUser.value = it
                    completionListener?.onSuccess()

                    return@launch
                }
                completionListener?.onFailure("no data saved for this user")
            }catch (e : Exception){
                completionListener?.onFailure(e.message!!)
            }

        }
    }

    fun turnUriToString(context: Context, uri: Uri){
        completionListener?.onStarted()
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val imageStream = context.contentResolver.openInputStream(uri)

                val selectedImage = BitmapFactory.decodeStream(imageStream)
                val baos = ByteArrayOutputStream()
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val b: ByteArray = baos.toByteArray()

                imageB64String = Base64.encodeToString(b, Base64.DEFAULT)
                withContext(Dispatchers.Main){
                    completionListener?.onChange()
                }
            }catch (e: FileNotFoundException){
                withContext(Dispatchers.Main){
                    completionListener?.onFailure(e.message!!)
                }
            }
        }
    }

}
