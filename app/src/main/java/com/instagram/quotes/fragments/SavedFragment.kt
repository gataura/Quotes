package com.instagram.quotes.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.memes.quotes.R
import com.instagram.quotes.adapter.SavedAdapter
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
class SavedFragment : Fragment(), QuotesView {

    private lateinit var presenter: QuotesApiPresenter

    private lateinit var quoteRecyclerView: RecyclerView
    private lateinit var quoteAdapter: SavedAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var db: AppDatabase
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    private lateinit var mInterstitialAd: InterstitialAd

    lateinit var myRef: DatabaseReference
    lateinit var database: FirebaseDatabase
    var dbValue: Long = 0
    var adCounter: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_saved, container, false)

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

        db = AppDatabase.getInstance(this.requireContext()) as AppDatabase


        mInterstitialAd = InterstitialAd(this.requireContext())
        mInterstitialAd.adUnitId = "ca-app-pub-9561253976720525/7301847397"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        mInterstitialAd.adListener = object: AdListener() {

            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }

        }

        presenter = QuotesApiPresenter()
        presenter.bind(this)

        mSwipeRefreshLayout = view.findViewById(R.id.saved_container)

        quoteRecyclerView = view.findViewById(R.id.saved_recycler_view)
        quoteRecyclerView.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        quoteAdapter = SavedAdapter(presenter)
        quoteRecyclerView.adapter = quoteAdapter
        quoteRecyclerView.layoutManager = layoutManager

        presenter.getItemsFromDb(db)

        mSwipeRefreshLayout.setOnRefreshListener {
            presenter.getItemsFromDb(db)
            mSwipeRefreshLayout.isRefreshing = false
        }

        return view
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

    override fun setQuote(quote: Quote) {

    }

    override fun onLoad() {
        quoteAdapter.notifyDataSetChanged()
    }

    override fun startIntent(sharingIntent: Intent) {
        this.requireContext().startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }


}
