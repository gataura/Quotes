package com.memes.quotes.presenter

import com.memes.quotes.api.model.Quote
import com.memes.quotes.api.service.mQuoteClient
import com.memes.quotes.presenter.base.BasePresenter
import com.memes.quotes.presenter.base.QuotesView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuotesApiPresenter: BasePresenter<QuotesView>(), IQuotesPresenter {

    var quote = Quote()

    val client = mQuoteClient().build()

    fun loadQuote() {
        getQuote("ru").enqueue(object: Callback<Quote> {
            override fun onResponse(call: Call<Quote>, response: Response<Quote>) {
                if (response.body() != null) {
                    view?.setQuote(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Quote>, t: Throwable) {

            }

        })
    }

    fun getQuote(lang: String) = client.getQuote(lang)

}