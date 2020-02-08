package com.example.h_mal.firebase_mobile_signin_app.ui

interface CompletionListener {
    fun onStarted()
    fun onChange()
    fun onSuccess()
    fun onFailure(message: String)
}