package com.instagram.quotes.presenter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.memes.quotes.R
import com.instagram.quotes.adapter.QuoteRowView
import com.instagram.quotes.api.model.Quote
import com.instagram.quotes.api.service.mQuoteClient
import com.instagram.quotes.database.AppDatabase
import com.instagram.quotes.presenter.base.BasePresenter
import com.instagram.quotes.presenter.base.QuotesView
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class QuotesApiPresenter: BasePresenter<QuotesView>(), IQuotesPresenter {

    private var quoteList = mutableListOf<Quote>()
    var quote = Quote()
    var lang = ""
    var counter = 0
    var adCounter = 0

    override fun getMemesCount(): Int {
        return quoteList.size
    }

    override fun onBindMemesRowViewAtPosition(position: Int, rowView: QuoteRowView) {
        quote = quoteList[position]
        rowView.setTitle(quote.getQuoteText())
        rowView.setAuthor(quote.getQuoteAuthor())
        rowView.setLikes(quote.getLikes())
        rowView.checkLiked(quote.getLiked())
        isMemesInDb(quote, view?.getDb() as AppDatabase, rowView)
    }

    private val client = mQuoteClient().build()

    fun loadQuote() {
        if (Locale.getDefault().language == "ru") {
            lang = "ru"
        } else {
            lang = "en"
        }
        getQuote(lang).enqueue(object: Callback<Quote> {
            override fun onResponse(call: Call<Quote>, response: Response<Quote>) {
                if (response.body() != null) {
                    quote = response.body()!!
                    quote.setLikes((12..470).random())
                    view?.setQuote(quote)
                }
            }

            override fun onFailure(call: Call<Quote>, t: Throwable) {

            }

        })
    }

    private fun getQuote(lang: String) = client.getQuote(lang)

    @SuppressLint("CheckResult")
    fun getItemsFromDb(db: AppDatabase) {
        Observable.fromCallable { db.quoteDao().getAll() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                quoteList.clear()
                quoteList.addAll(it)
                view?.onLoad()
            }, {
            })
    }

    fun onSaveIconClickAction(img: ImageView, adapterPosition: Int) {
        if (img.tag == "saved") {
            img.setImageResource(R.drawable.save_icon_outline_24)
            img.tag = "not saved"
            deleteFromDb(quoteList[adapterPosition], view?.getDb() as AppDatabase)
        } else {
            img.setImageResource(R.drawable.save_icon_24)
            img.tag = "saved"
            saveToDb(quoteList[adapterPosition], view?.getDb() as AppDatabase)
        }

        counterAd()
    }

    fun onShareIconClick(){
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Look at this, i found it in Quote app: ")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, quote.getQuoteText())
        view?.startIntent(sharingIntent)

        counterAd()
    }

    fun onShareIconClick(adapterPosition: Int){
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Look at this, i found it in Quote app: ")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, quoteList[adapterPosition].getQuoteText())
        view?.startIntent(sharingIntent)

        counterAd()
    }

    fun onSaveIconClick(img: ImageView) {
        if (img.tag == "saved") {
            img.setImageResource(R.drawable.save_icon_outline_24)
            img.tag = "not saved"
            deleteFromDb(view?.getDb() as AppDatabase)
        } else {
            img.setImageResource(R.drawable.save_icon_24)
            img.tag = "saved"
            saveToDb(view?.getDb() as AppDatabase)
        }

        counterAd()
    }

    fun onLikeClick(img: ImageView, likesCount: TextView){
        if (img.tag == "liked") {
            img.setImageResource(R.drawable.like_outline)
            img.tag = "not liked"
            quote.setLiked(false)
            quote.setLikes(quote.getLikes() - 1)
            likesCount.text = quote.getLikes().toString()
        } else {
            img.setImageResource(R.drawable.like_filled)
            img.tag = "liked"
            quote.setLiked(true)
            quote.setLikes(quote.getLikes() + 1)
            likesCount.text = quote.getLikes().toString()
        }

        counterAd()
    }

    fun onLikeClickAction(img: ImageView, likesCount: TextView, adapterPosition: Int){
        if (img.tag == "liked") {
            img.setImageResource(R.drawable.like_outline)
            img.tag = "not liked"
            quoteList[adapterPosition].setLiked(false)
            quoteList[adapterPosition].setLikes(quoteList[adapterPosition].getLikes() - 1)
            likesCount.text = quoteList[adapterPosition].getLikes().toString()
        } else {
            img.setImageResource(R.drawable.like_filled)
            img.tag = "liked"
            quoteList[adapterPosition].setLiked(true)
            quoteList[adapterPosition].setLikes(quoteList[adapterPosition].getLikes() + 1)
            likesCount.text = quoteList[adapterPosition].getLikes().toString()
        }

        counterAd()
    }

    @SuppressLint("CheckResult")
    fun deleteFromDb(data: Quote, db: AppDatabase) {

        Completable.fromAction{ db.quoteDao().delete(data) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
                Log.d("SaveToDb", "Again", it)
            })

    }

    @SuppressLint("CheckResult")
    fun deleteFromDb(db: AppDatabase) {

        Completable.fromAction{ db.quoteDao().deleteCustom(quote.getQuoteLink()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
                Log.d("SaveToDb", "Again", it)
            })

    }

    @SuppressLint("CheckResult")
    fun isMemesInDb(data: Quote, db: AppDatabase, rowView: QuoteRowView) {

        Observable.fromCallable { db.quoteDao().quoteCount(data.getQuoteLink()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it > 0) {
                    rowView.setIcon()
                }
            }

    }

    @SuppressLint("CheckResult")
    fun isMemesInDb(data: Quote, db: AppDatabase) {

        Observable.fromCallable { db.quoteDao().quoteCount(data.getQuoteLink()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it > 0) {
                }
            }

    }

    @SuppressLint("CheckResult")
    fun saveToDb(db: AppDatabase) {

        Completable.fromAction{ db.quoteDao().insert(quote) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                Log.d("SaveToDb", "Again", it)
            })

    }

    @SuppressLint("CheckResult")
    fun saveToDb(data: Quote, db: AppDatabase) {

        Completable.fromAction{ db.quoteDao().insert(data) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                Log.d("SaveToDb", "Again", it)
            })

    }

    fun counterAd() {
        counter++
        if (counter % view!!.getOpenAd() == 0) {
            if (view?.getAd()!!.isLoaded) {
                if (adCounter != view!!.getAdCounter()) {
                    view?.getAd()?.show()
                    adCounter++
                }
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.")
            }

        }
    }

}