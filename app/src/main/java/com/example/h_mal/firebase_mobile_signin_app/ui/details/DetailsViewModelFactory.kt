package com.example.h_mal.firebase_mobile_signin_app.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.h_mal.firebase_mobile_signin_app.data.repository.Repository

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory (
    private val repository: Repository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(repository) as T
    }
}