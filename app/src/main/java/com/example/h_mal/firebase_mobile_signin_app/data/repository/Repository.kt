package com.example.h_mal.firebase_mobile_signin_app.data.repository

import com.example.h_mal.firebase_mobile_signin_app.data.firebase.FirebaseSource
import com.example.h_mal.firebase_mobile_signin_app.data.network.MyApi
import com.example.h_mal.firebase_mobile_signin_app.data.network.SafeApiRequest
import com.example.h_mal.firebase_mobile_signin_app.data.network.model.ResponseObject
import com.example.h_mal.firebase_mobile_signin_app.data.network.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthProvider

class Repository(
    private val firebaseSource: FirebaseSource,
    private val api: MyApi
): SafeApiRequest() {


    fun signIn(verificationId : String, code: String): Task<AuthResult> {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)

        return firebaseSource.signInWithPhoneNumber(credential)
    }

    fun getCode(string: String, callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks)
        = firebaseSource.sendVerificationCode(string, callback)

    fun getUid() = firebaseSource.getUid()

    fun logoutUser() = firebaseSource.logout()

    suspend fun getUser(uid: String): ResponseObject{
        return apiRequest { api.getUser(uid) }
    }

    suspend fun updateUser(user: User): ResponseObject{
        return apiRequest { api.updateUser(user) }
    }
}