package com.instagram.quotes.api.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotesData")
class Quote (
    @PrimaryKey (autoGenerate = true) private var id: Int = 0,
    @ColumnInfo (name = "quoteText") private var quoteText: String = "",
    @ColumnInfo (name = "quoteAuthor")private var quoteAuthor: String = "",
    @ColumnInfo (name = "senderName")private var senderName: String = "",
    @ColumnInfo (name = "senderLink")private var senderLink: String = "",
    @ColumnInfo (name = "quoteLink") private var quoteLink: String = "",
    @ColumnInfo (name = "likes") private var likes: Int = 0,
    @ColumnInfo (name = "liked") private var liked: Boolean = false
) {
    fun getQuoteText(): String {
        return quoteText
    }

    fun getQuoteAuthor(): String {
        return quoteAuthor
    }

    fun getSenderName(): String {
        return senderName
    }

    fun getSenderLink(): String {
        return senderLink
    }

    fun getQuoteLink(): String {
        return quoteLink
    }

    fun getId(): Int {
        return id
    }

    fun getLikes(): Int {
        return likes
    }

    fun setLikes(likes: Int) {
        this.likes = likes
    }

    fun getLiked(): Boolean {
        return liked
    }

    fun setLiked(liked: Boolean) {
        this.liked = liked
    }
}