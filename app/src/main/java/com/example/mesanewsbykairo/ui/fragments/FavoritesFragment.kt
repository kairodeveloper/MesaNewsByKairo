package com.example.mesanewsbykairo.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mesanewsbykairo.adapters.BtnClickListener
import com.example.mesanewsbykairo.adapters.BtnClickListenerFavorites
import com.example.mesanewsbykairo.adapters.RecyclerViewFavoriteAdapter
import com.example.mesanewsbykairo.adapters.RecyclerViewNewsAdapter
import com.example.mesanewsbykairo.models.NewsDB
import com.example.mesanewsbykairo.services.beans.NewsModel
import com.example.mesanewsbykairo.ui.MovableFloatingActionButton
import com.example.mesanewsbykairo.ui.NewsDetailActivity
import com.example.mesanewsbykairo.ui.R
import com.google.gson.Gson
import com.orm.SugarRecord

class FavoritesFragment : Fragment(), BtnClickListenerFavorites {
    private var list = ArrayList<NewsDB>()
    private var recyclerView: RecyclerView? = null
    private var recyclerViewNewsAdapter: RecyclerViewFavoriteAdapter? = null
    private var btnFabFragmentNews: MovableFloatingActionButton? = null
    private val linearLayoutManager = LinearLayoutManager(context)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onStart() {
        super.onStart()

        val newsModelTop = NewsDB()
        newsModelTop.isTitle = true
        list.add(0, newsModelTop)

        val lista = SugarRecord.findAll(NewsDB::class.java)
        lista.forEach {
            list.add(it)
        }

        initializeData()
    }

    private fun initializeData() {
        recyclerViewNewsAdapter = RecyclerViewFavoriteAdapter(list, requireContext(), this)
        recyclerView = activity?.findViewById(R.id.rv_favorites)

        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.adapter = recyclerViewNewsAdapter

        btnFabFragmentNews = activity?.findViewById(R.id.btnFabFragmentNews)
        btnFabFragmentNews?.setOnClickListener {
            recyclerView?.smoothScrollToPosition(0)
        }
    }

    override fun onBtnClick(obj: NewsDB) {
        val intent = Intent(context, NewsDetailActivity::class.java)
        intent.putExtra("news", Gson().toJson(obj))
        startActivity(intent)
    }
}