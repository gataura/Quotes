package com.instagram.quotes.presenter

import com.instagram.quotes.adapter.QuoteRowView

interface IQuotesPresenter{
    fun onBindMemesRowViewAtPosition(position: Int, rowView: QuoteRowView)
    fun getMemesCount(): Int
}