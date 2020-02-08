package com.example.h_mal.firebase_mobile_signin_app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.h_mal.firebase_mobile_signin_app.data.repository.Repository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory (
    private val repository: Repository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}