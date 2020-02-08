package com.example.h_mal.firebase_mobile_signin_app.data.firebase

import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit

class FirebaseSource {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser

    fun getUid() = firebaseAuth.currentUser!!.uid

    fun signInWithPhoneNumber(credentials: PhoneAuthCredential) = firebaseAuth.signInWithCredential(credentials)

    fun sendVerificationCode(phoneNo: String, mCallback : OnVerificationStateChangedCallbacks)
            = PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNo,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            mCallback)


}