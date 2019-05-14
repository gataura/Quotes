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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FeedFragment : Fragment() {

    private lateinit var presenter: QuotesApiPresenter
    var quote = Quote()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_feed, container, false)

        presenter = QuotesApiPresenter()
        quote = presenter.loadQuote()

        var quote_text_view = view.findViewById<TextView>(R.id.quote_text_view)
        quote_text_view.text = quote.getQuoteText()

        return view
        }
    }

