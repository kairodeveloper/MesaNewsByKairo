package com.example.mesanewsbykairo.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mesanewsbykairo.ui.fragments.FavoritesFragment
import com.example.mesanewsbykairo.ui.fragments.NewsFragment
import com.example.mesanewsbykairo.ui.fragments.ProfileFragment


const val HOME = "home_fragment"
const val FAVORITES = "favorites_fragment"
const val PROFILE = "profile_fragment"

class NewsActivity : AppCompatActivity() {
    private var screenSelected = HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

    }

    override fun onResume() {
        super.onResume()

        val fragmentManager = supportFragmentManager.beginTransaction()

        val newFragment: Fragment = if (screenSelected == HOME) {
            NewsFragment()
        } else {
            FavoritesFragment()
        }

        fragmentManager.replace(R.id.fragment_activity_news, newFragment)
        fragmentManager.addToBackStack(null)
        fragmentManager.commit()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun screensSelector(button: View) {
        val fragmentManager = supportFragmentManager.beginTransaction()

        val btnHomeSelected: ImageButton =  findViewById(R.id.btn_home_selected)
        val btnHomeNonSelected: ImageButton =  findViewById(R.id.btn_home_non_selected)
        val btnSearchSelected: ImageButton =  findViewById(R.id.btn_search_selected)
        val btnSearchNonSelected: ImageButton =  findViewById(R.id.btn_search_non_selected)
        val btnFavoritesSelected: ImageButton =  findViewById(R.id.btn_favorites_selected)
        val btnFavoritesNonSelected: ImageButton =  findViewById(R.id.btn_favorites_non_selected)
        val btnProfileSelected: ImageButton =  findViewById(R.id.btn_profile_selected)
        val btnProfileNonSelected: ImageButton =  findViewById(R.id.btn_profile_non_selected)

        when {
            button.tag.toString() == "btn_home" -> {
                if (screenSelected != HOME) {
                    val newFragment =
                        NewsFragment()
                    fragmentManager.replace(R.id.fragment_activity_news, newFragment)
                    screenSelected = HOME
                }

                btnHomeSelected.visibility = View.VISIBLE
                btnHomeNonSelected.visibility = View.GONE

                btnSearchSelected.visibility = View.GONE
                btnSearchNonSelected.visibility = View.VISIBLE
                btnFavoritesSelected.visibility = View.GONE
                btnFavoritesNonSelected.visibility = View.VISIBLE
                btnProfileSelected.visibility = View.GONE
                btnProfileNonSelected.visibility = View.VISIBLE
            }
            button.tag.toString() == "btn_favorites" -> {
                if (screenSelected != FAVORITES) {
                    val newFragment =
                        FavoritesFragment()
                    fragmentManager.replace(R.id.fragment_activity_news, newFragment)
                    screenSelected = FAVORITES
                }

                btnFavoritesSelected.visibility = View.VISIBLE
                btnFavoritesNonSelected.visibility = View.GONE

                btnHomeSelected.visibility = View.GONE
                btnHomeNonSelected.visibility = View.VISIBLE
                btnSearchSelected.visibility = View.GONE
                btnSearchNonSelected.visibility = View.VISIBLE
                btnProfileSelected.visibility = View.GONE
                btnProfileNonSelected.visibility = View.VISIBLE
            }
            button.tag.toString() == "btn_search" -> {
                btnSearchSelected.visibility = View.VISIBLE
                btnSearchNonSelected.visibility = View.GONE

                btnHomeSelected.visibility = View.GONE
                btnHomeNonSelected.visibility = View.VISIBLE
                btnFavoritesSelected.visibility = View.GONE
                btnFavoritesNonSelected.visibility = View.VISIBLE
                btnProfileSelected.visibility = View.GONE
                btnProfileNonSelected.visibility = View.VISIBLE
            }
            else -> {
                if (screenSelected != PROFILE) {
                    val newFragment = ProfileFragment()
                    fragmentManager.replace(R.id.fragment_activity_news, newFragment)
                    screenSelected = PROFILE
                }

                btnProfileSelected.visibility = View.VISIBLE
                btnProfileNonSelected.visibility = View.GONE

                btnHomeSelected.visibility = View.GONE
                btnHomeNonSelected.visibility = View.VISIBLE
                btnSearchSelected.visibility = View.GONE
                btnSearchNonSelected.visibility = View.VISIBLE
                btnFavoritesSelected.visibility = View.GONE
                btnFavoritesNonSelected.visibility = View.VISIBLE
            }
        }

        fragmentManager.addToBackStack(null)
        fragmentManager.commit()
    }
}