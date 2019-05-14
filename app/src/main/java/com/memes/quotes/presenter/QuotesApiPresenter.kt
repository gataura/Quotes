package com.memes.quotes.presenter

import com.memes.quotes.api.model.Quote
import com.memes.quotes.api.service.mQuoteClient
import com.memes.quotes.presenter.base.BasePresenter
import com.memes.quotes.presenter.base.QuotesView

class QuotesApiPresenter: BasePresenter<QuotesView>(), IQuotesPresenter {

    var quote = Quote()

    val client = mQuoteClient().build()

    fun loadQuote(): Quote {
        quote = getQuote("ru")
        return quote
    }

    fun getQuote(lang: String) = client.getQuote(lang)

}