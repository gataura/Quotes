package com.instagram.quotes.database

import androidx.room.*
import com.instagram.quotes.api.model.Quote

@Dao
interface QuoteDao {
    @Insert
    fun insert(quote: Quote)

    @Update
    fun update(quote: Quote)

    @Delete
    fun delete(quote: Quote)

    @Query("SELECT COUNT(*) from quotesData WHERE quoteLink = :url")
    fun quoteCount(url: String): Int

    @Query("SELECT * from quotesData")
    fun getAll(): List<Quote>

    @Query("DELETE FROM quotesData  WHERE quoteLink = :url")
    fun deleteCustom(url: String)

}