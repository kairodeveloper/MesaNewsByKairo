package com.example.mesanewsbykairo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
import com.example.mesanewsbykairo.utils.getToken
import com.google.gson.Gson
import pl.droidsonroids.gif.GifImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class NewsFragment : Fragment(), BtnClickListener {
    private var list = ArrayList<NewsModel>()
    private var listHighlight = ArrayList<NewsModel>()
    private var listFiltered = ArrayList<NewsModel>()
    private var currentPage = 1
    private var currentPageFiltered = 1
    private var token = getToken()
    private var loader: GifImageView? = null
    private var recyclerView: RecyclerView? = null
    private var recyclerViewNewsAdapter: RecyclerViewNewsAdapter? = null
    private var btnFabFragmentNews: MovableFloatingActionButton? = null
    private val linearLayoutManager = LinearLayoutManager(context)
    private var btnFilterNews: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar?.hide()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onStart() {
        super.onStart()

        btnFilterNews = activity?.findViewById(R.id.btn_filter_news)
        initializeData()
    }

    override fun onResume() {
        super.onResume()

        btnFilterNews?.setOnClickListener {
            val alertDialogBuilder = activity?.let { it1 -> AlertDialog.Builder(it1) }
            val view: View = layoutInflater.inflate(R.layout.fragment_filter, null)

            val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
            val editTextTitle = view.findViewById<EditText>(R.id.et_filter_titulo)

            alertDialogBuilder!!.setPositiveButton("Aplicar filtros") { dialog, _ ->
                val lista1 = ArrayList<NewsModel>()
//                Toast.makeText(context, getDateFromDatePicker(datePicker), Toast.LENGTH_SHORT)
//                    .show()

                val response =
                    RetrofitInitializer().newsService().getNewsByPublishedData(
                        "application/json",
                        token,
                        currentPageFiltered,
                        getDateFromDatePicker(datePicker)
                    )
                response.enqueue(object : Callback<NewsResponse> {
                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<NewsResponse>,
                        response: Response<NewsResponse>
                    ) {
                        val newList = ArrayList<NewsModel>()
                        val newListHighlights = ArrayList<NewsModel>()
                        if (listFiltered.size > 0) {
                            listFiltered.removeAt(listFiltered.size - 1)
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

                        listFiltered = newList
                        lista1.addAll(newList)
                    }
                })



                if (editTextTitle.text.toString().isNotEmpty()) {
                    listFiltered.map { newsModel ->
                        if (newsModel.title != null) {
                            if (newsModel.title.toLowerCase(Locale.getDefault()).contains(
                                    editTextTitle.text.toString().toLowerCase(Locale.getDefault())
                                )
                            ) {
                                lista1.add(newsModel)
                            }
                        }
                    }
                }

                listFiltered = lista1
                recyclerViewNewsAdapter?.changeList(lista1)
                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton("Limpar filtros") { dialog, _ ->
                recyclerViewNewsAdapter?.changeList(list)
                dialog.dismiss()
            }
            alertDialogBuilder.setView(view)
            alertDialogBuilder.show()

        }
    }

    private fun getDateFromDatePicker(datePicker: DatePicker): String {
        var dayDP = datePicker.dayOfMonth.toString()
        if (dayDP.toInt() < 10) {
            dayDP = ("0").plus(dayDP)
        }

        var monthDP = datePicker.month.plus(1).toString()
        if (monthDP.toInt() < 10) {
            monthDP = ("0").plus(monthDP)
        }

        val yearDP = datePicker.year.toString()

        return yearDP.plus("-").plus(monthDP).plus("-").plus(dayDP)
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

        recyclerViewNewsAdapter =
            RecyclerViewNewsAdapter(list, listHighlight, requireContext(), this)
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
        listFiltered = newList
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