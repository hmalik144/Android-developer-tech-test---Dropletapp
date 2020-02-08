package com.example.h_mal.firebase_mobile_signin_app.data.network

import com.example.h_mal.firebase_mobile_signin_app.data.network.model.ResponseObject
import com.example.h_mal.firebase_mobile_signin_app.data.network.model.User
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    @PUT("updateUser")
    suspend fun updateUser(
        @Body user: User
    ): Response<ResponseObject>

    @GET("getUser")
    suspend fun getUser(
        @Query("userId") userId: String
    ) : Response<ResponseObject>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : MyApi{

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://us-central1-test-project-hc.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}

