package com.grinleaf.ex101naversearchapi.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitHelper {
    companion object{
        fun getRetrofitInstance():Retrofit{
            val retrofit:Retrofit= Retrofit.Builder().baseUrl("https://openapi.naver.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }
    }
}