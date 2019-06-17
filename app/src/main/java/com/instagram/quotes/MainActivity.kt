package com.instagram.quotes

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.*
import com.instagram.quotes.fragments.FeedFragment
import com.instagram.quotes.fragments.SavedFragment
import com.instagram.quotes.helper.find
import com.memes.quotes.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fragmentFeed = FeedFragment()
    private val fragmentSaved = SavedFragment()
    private val fm = supportFragmentManager
    var active: Fragment = fragmentFeed

    lateinit var adView: AdView

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_feed -> {
                fm.beginTransaction().hide(active).show(fragmentFeed).commit()
                active = fragmentFeed

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_saved -> {
                fm.beginTransaction().hide(active).detach(fragmentSaved).attach(fragmentSaved).show(fragmentSaved)
                    .commit()
                active = fragmentSaved

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fm.beginTransaction().add(R.id.main_fragment, fragmentSaved, "2").hide(fragmentSaved).commit()
        fm.beginTransaction().add(R.id.main_fragment, fragmentFeed, "1").commit()


        MobileAds.initialize(this, "ca-app-pub-9561253976720525~8874729610")
        adView = find(R.id.adViewBanner)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        adView.adListener = object: AdListener() {
            override fun onAdClosed() {
                adView.loadAd(adRequest)
            }

        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}