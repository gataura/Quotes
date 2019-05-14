package com.memes.quotes.api.model

class Quote (
    private var quoteText: String = "",
    private var quoteAuthor: String = "",
    private var senderName: String = "",
    private var senderLink: String = "",
    private var quoteLink: String = ""
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
}