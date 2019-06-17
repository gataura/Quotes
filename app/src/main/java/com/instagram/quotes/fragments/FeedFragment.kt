package com.instagram.quotes.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.memes.quotes.R
import com.instagram.quotes.api.model.Quote
import com.instagram.quotes.database.AppDatabase
import com.instagram.quotes.presenter.QuotesApiPresenter
import com.instagram.quotes.presenter.base.QuotesView

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
    lateinit var author_text_view: TextView
    lateinit var save_icon: ImageView
    lateinit var quote_card: CardView
    lateinit var refreshButton: View
    lateinit var shareIcon: ImageView
    lateinit var likeIcon: ImageView
    lateinit var likesCount: TextView

    lateinit var myRef: DatabaseReference
    lateinit var database: FirebaseDatabase
    var dbValue: Long = 0
    var adCounter: Long = 0

    var isFirstLoad = true

    private lateinit var db: AppDatabase

    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_feed, container, false)

        db = AppDatabase.getInstance(this.requireContext()) as AppDatabase

        FirebaseApp.initializeApp(this.requireContext())
        database = FirebaseDatabase.getInstance()
        myRef = database.reference

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                dbValue = p0.child("openAd").value as Long
                adCounter = p0.child("adCounter").value as Long
            }

        })


        mInterstitialAd = InterstitialAd(this.requireContext())
        mInterstitialAd.adUnitId = "ca-app-pub-9561253976720525/7301847397"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        mInterstitialAd.adListener = object: AdListener() {

            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }

            override fun onAdLoaded() {
                if (isFirstLoad) {
                    mInterstitialAd.show()
                    isFirstLoad = false
                }
            }

        }


        quote_text_view = view.findViewById(R.id.quote_text_view)
        author_text_view = view.findViewById(R.id.author_text_view)
        quote_card = view.findViewById(R.id.quote_card)
        refreshButton = view.findViewById(R.id.refreshButton)
        save_icon = view.findViewById(R.id.save_icon)
        shareIcon = view.findViewById(R.id.share_icon)
        likeIcon = view.findViewById(R.id.like_icon)
        likesCount = view.findViewById(R.id.likes_count)

        refreshButton.setOnClickListener {
            presenter.loadQuote()
            presenter.counterAd()
        }

        save_icon.setOnClickListener {
            presenter.onSaveIconClick(save_icon)
        }

        shareIcon.setOnClickListener {
            presenter.onShareIconClick()
        }

        likeIcon.setOnClickListener {
            presenter.onLikeClick(likeIcon, likesCount)
        }

        quote_card.setOnClickListener {
            presenter.loadQuote()
            presenter.counterAd()
        }

        presenter = QuotesApiPresenter()
        presenter.bind(this)
        presenter.loadQuote()

        return view
    }

    override fun setQuote(quote: Quote) {
        quote_text_view.text = quote.getQuoteText()
        author_text_view.text = quote.getQuoteAuthor()
        save_icon.setImageResource(R.drawable.save_icon_outline_24)
        save_icon.tag = "not saved"
        likeIcon.setImageResource(R.drawable.like_outline)
        likeIcon.tag = "not liked"
        likesCount.text = quote.getLikes().toString()
    }

    override fun getDb(): AppDatabase {
        return db
    }

    override fun getAd(): InterstitialAd {
        return mInterstitialAd
    }

    override fun getOpenAd(): Int {
        return dbValue.toInt()
    }

    override fun getAdCounter(): Int {
        return adCounter.toInt()
    }

    override fun onLoad() {

    }

    override fun startIntent(sharingIntent: Intent) {
        this.requireContext().startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }
}

