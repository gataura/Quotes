package com.instagram.quotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.memes.quotes.R
import com.instagram.quotes.presenter.QuotesApiPresenter

class SavedAdapter(memesPresenter: QuotesApiPresenter): RecyclerView.Adapter<QuoteViewHolder>() {

    var presenter: QuotesApiPresenter = memesPresenter

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        presenter.onBindMemesRowViewAtPosition(position, holder)
    }

    override fun getItemCount(): Int {
        return presenter.getMemesCount()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        return QuoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.quote_item_view, parent, false),
            presenter
        )
    }
}