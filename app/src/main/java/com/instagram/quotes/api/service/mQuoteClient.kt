package com.instagram.quotes.api.service

import com.instagram.quotes.helper.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class mQuoteClient {

    private var builder = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    private val retrofit: Retrofit by lazy {
        builder.build()
    }

    private val client: QuoteClient by lazy {
        retrofit.create(QuoteClient::class.java)
    }

    fun build() = client

}