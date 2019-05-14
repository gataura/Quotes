package com.memes.quotes.presenter.base

import com.memes.quotes.api.model.Quote

interface QuotesView: BaseView {
    fun setQuote(quote: Quote)
}