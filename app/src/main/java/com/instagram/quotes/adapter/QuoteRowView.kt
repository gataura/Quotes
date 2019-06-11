package com.instagram.quotes.adapter

interface QuoteRowView {
    fun setTitle(title: String)
    fun setIcon()
    fun setAuthor(author: String)
    fun setLikes(likes: Int)
    fun checkLiked(flag: Boolean)
}