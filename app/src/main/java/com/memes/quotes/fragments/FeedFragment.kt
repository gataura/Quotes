package com.memes.quotes.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.memes.quotes.R
import com.memes.quotes.api.model.Quote
import com.memes.quotes.presenter.QuotesApiPresenter
import com.memes.quotes.presenter.base.QuotesView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FeedFragment : Fragment(), QuotesView {

    private lateinit var presenter: QuotesApiPresenter

    lateinit var quote_text_view: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_feed, container, false)

        quote_text_view = view.findViewById(R.id.quote_text_view)

        presenter = QuotesApiPresenter()
        presenter.bind(this)
        presenter.loadQuote()

        return view
    }

    override fun setQuote(quote: Quote) {
        quote_text_view.text = quote.getQuoteText()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }
}

