package com.example.h_mal.firebase_mobile_signin_app

import android.app.Application
import com.example.h_mal.firebase_mobile_signin_app.data.repository.Repository
import com.example.h_mal.firebase_mobile_signin_app.data.firebase.FirebaseSource
import com.example.h_mal.firebase_mobile_signin_app.data.network.MyApi
import com.example.h_mal.firebase_mobile_signin_app.data.network.NetworkConnectionInterceptor
import com.example.h_mal.firebase_mobile_signin_app.ui.details.DetailsViewModel
import com.example.h_mal.firebase_mobile_signin_app.ui.details.DetailsViewModelFactory
import com.example.h_mal.firebase_mobile_signin_app.ui.main.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class AppClass : Application(), KodeinAware{


    override val kodein=  Kodein.lazy {
        import(androidXModule(this@AppClass))

        bind() from singleton { FirebaseSource() }
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { Repository(instance(),instance()) }

        bind() from provider { MainViewModelFactory(instance()) }
        bind() from provider { DetailsViewModelFactory(instance()) }
    }

}