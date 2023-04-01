package com.example.ehhhhhh.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object YDictInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://dictionary.yandex.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: YDictInterface by lazy {
        retrofit.create(YDictInterface::class.java)
    }
}