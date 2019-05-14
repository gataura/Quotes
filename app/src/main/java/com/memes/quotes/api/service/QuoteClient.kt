package com.memes.quotes.api.service

import com.memes.quotes.api.model.Quote
import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteClient {

    @GET("api/1.0/?method=getQuote&format=json")
    fun getQuote(@Query("lang") lang: String): Call<Quote>

}