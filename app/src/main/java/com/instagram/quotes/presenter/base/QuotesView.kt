package com.instagram.quotes.presenter.base

import android.content.Intent
import com.google.android.gms.ads.InterstitialAd
import com.instagram.quotes.api.model.Quote
import com.instagram.quotes.database.AppDatabase

interface QuotesView: BaseView {
    fun setQuote(quote: Quote)
    fun getDb(): AppDatabase
    fun onLoad()
    fun startIntent(sharingIntent: Intent)
    fun getAd(): InterstitialAd
    fun getOpenAd(): Int
}