package com.example.h_mal.firebase_mobile_signin_app.ui.main

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.h_mal.firebase_mobile_signin_app.data.repository.Repository
import com.example.h_mal.firebase_mobile_signin_app.ui.CompletionListener
import com.example.h_mal.firebase_mobile_signin_app.ui.details.DetailsUpdateActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    var completionListener : CompletionListener? = null

    var phoneNumber: String? = null
    var verificationCode: String? = null

    var verificationId: String? = null

    fun onVerificationNumberClick(view: View){
        completionListener?.onStarted()
        if(phoneNumber.isNullOrEmpty()){
            completionListener?.onFailure("Invalid Phone Number")
            return
        }

        if (phoneNumber!!.count() < 6){
            completionListener?.onFailure("Invalid Phone number")
            return
        }

        repository.getCode("+$phoneNumber", mCallBack)
    }

    fun onSignInClick(view: View){
        if(verificationCode.isNullOrEmpty() || verificationId.isNullOrEmpty()){
            completionListener?.onFailure("Invalid verification code or ID")
            return
        }

        repository.signIn(verificationId!!,verificationCode!!).addOnCompleteListener{
            if (it.isSuccessful){
                Intent(view.context, DetailsUpdateActivity::class.java).apply {
                    this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    view.context.startActivity(this)
                }
            }else{
                completionListener?.onFailure(it.exception?.message!!)
            }
        }
    }

    private val mCallBack: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(
                s: String,
                forceResendingToken: ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                verificationId = s
                completionListener?.onChange()

                if (phoneNumber == "447849979363"){
                    //test accounts do not complete verifications
                    //lets stop the spinner
                    completionListener?.onSuccess()
                }
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                completionListener?.onSuccess()
                phoneAuthCredential.smsCode?.let {
                    verificationCode = it
                    return
                }
                completionListener?.onFailure("Failed To Verify")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                completionListener?.onFailure(e.message!!)
            }
        }

}
