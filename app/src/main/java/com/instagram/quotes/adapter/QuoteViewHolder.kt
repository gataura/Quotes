package com.instagram.quotes.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.memes.quotes.R
import com.instagram.quotes.presenter.QuotesApiPresenter

class QuoteViewHolder(itemView: View, presenter: QuotesApiPresenter): RecyclerView.ViewHolder(itemView),
    QuoteRowView {

    var quoteTitle: TextView = itemView.findViewById(R.id.saved_quote_text_view)
    var quoteAuthor: TextView = itemView.findViewById(R.id.saved_author_text_view)
    var saveIcon: ImageView = itemView.findViewById(R.id.saved_save_icon)
    var shareIcon: ImageView = itemView.findViewById(R.id.saved_share_icon)
    var likeIcon: ImageView = itemView.findViewById(R.id.saved_like_icon)
    var likesCount: TextView = itemView.findViewById(R.id.saved_likes_count)


    init {

        shareIcon.setOnClickListener {
            presenter.onShareIconClick(adapterPosition)
        }

        saveIcon.setOnClickListener {
            presenter.onSaveIconClickAction(saveIcon, adapterPosition)
        }

        likeIcon.setOnClickListener {
            presenter.onLikeClickAction(likeIcon, likesCount, adapterPosition)
        }

    }


    override fun setTitle(title: String) {
        quoteTitle.text = title
    }

    override fun setAuthor(author: String) {
        quoteAuthor.text = author
    }

    override fun setIcon() {
        saveIcon.setImageResource(R.drawable.save_icon_24)
        saveIcon.tag = "saved"
    }

    override fun checkLiked(flag: Boolean) {
        if (flag) {
            likeIcon.setImageResource(R.drawable.like_filled)
            likeIcon.tag = "liked"
        } else {
            likeIcon.setImageResource(R.drawable.like_outline)
            likeIcon.tag = "not liked"
        }
    }

    override fun setLikes(likes: Int) {
        likesCount.text = likes.toString()
    }


}