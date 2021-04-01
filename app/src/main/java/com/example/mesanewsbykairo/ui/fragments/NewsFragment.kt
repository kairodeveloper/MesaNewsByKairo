package com.example.mesanewsbykairo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mesanewsbykairo.adapters.BtnClickListener
import com.example.mesanewsbykairo.adapters.RecyclerViewNewsAdapter
import com.example.mesanewsbykairo.services.RetrofitInitializer
import com.example.mesanewsbykairo.services.beans.NewsModel
import com.example.mesanewsbykairo.services.beans.NewsResponse
import com.example.mesanewsbykairo.ui.MovableFloatingActionButton
import com.example.mesanewsbykairo.ui.NewsDetailActivity
import com.example.mesanewsbykairo.ui.R
import com.google.gson.Gson
import pl.droidsonroids.gif.GifImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment(), BtnClickListener {
    private var list = ArrayList<NewsModel>()
    private var listHighlight = ArrayList<NewsModel>()
    private var currentPage = 1
    private var token =
        "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjU5LCJlbWFpbCI6ImRpbWFzLmdhYnJpZWxAenJvYmFuay5jb20uYnIifQ.a3j7sRx8FIedZCfDGLocduOYpcibfIenX7TVJjv6Sis"
    private var loader: GifImageView? = null
    private var recyclerView: RecyclerView? = null
    private var recyclerViewNewsAdapter: RecyclerViewNewsAdapter? = null
    private var btnFabFragmentNews: MovableFloatingActionButton? = null
    private val linearLayoutManager = LinearLayoutManager(context)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onStart() {
        super.onStart()

        initializeData()
    }

    private fun getNews() {
        val response =
            RetrofitInitializer().newsService().getNews("application/json", token, currentPage)
        response.enqueue(object : Callback<NewsResponse> {
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.code() == 200) {
                    val newList = ArrayList<NewsModel>()
                    val newListHighlights = ArrayList<NewsModel>()
                    if (list.size > 0) {
                        list.removeAt(list.size - 1)
                    } else {
                        val newsModelTop = NewsModel()
                        newsModelTop.isANew = false
                        newsModelTop.isTitle = true
                        newList.add(0, newsModelTop)
                    }

                    response.body()?.data?.map { it ->
                        if (it.highlight!!) {
                            newListHighlights.add(it)
                        } else {
                            newList.add(it)
                        }
                    }

                    val newsModel = NewsModel()
                    newsModel.isANew = false
                    newsModel.isTitle = false
                    newList.add(newsModel)

                    updateData(newList, newListHighlights)

                    currentPage += 1
                } else {
                    Toast.makeText(context, getString(R.string.error_req_news), Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }

    private fun initializeData() {
        loader = activity?.findViewById(R.id.img_load)

        recyclerViewNewsAdapter = RecyclerViewNewsAdapter(list, listHighlight, requireContext(), this)
        recyclerView = activity?.findViewById(R.id.rv_news)

        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.adapter = recyclerViewNewsAdapter

        btnFabFragmentNews = activity?.findViewById(R.id.btnFabFragmentNews)
        btnFabFragmentNews?.setOnClickListener {
            recyclerView?.smoothScrollToPosition(0)
        }

        getNews()
    }

    private fun updateData(newList: ArrayList<NewsModel>, newListHighligh: ArrayList<NewsModel>) {
        recyclerViewNewsAdapter?.removeButtonMoreItems()
        recyclerViewNewsAdapter?.setDataOnList(newList)
        recyclerViewNewsAdapter?.setDataOnHighlight(newListHighligh)


        list = newList
        listHighlight = newListHighligh

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        loader?.visibility = View.GONE
        recyclerView?.visibility = View.VISIBLE
        btnFabFragmentNews?.visibility = View.VISIBLE
    }

    override fun onBtnClick(obj: NewsModel) {
        if (obj.isANew) {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra("news", Gson().toJson(obj))
            startActivity(intent)
        } else {
            getNews()
        }
    }

}