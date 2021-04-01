package com.example.mesanewsbykairo.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.mesanewsbykairo.adapters.BtnClickListenerShare
import com.example.mesanewsbykairo.services.beans.NewsModel
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class NewsDetailActivity : AppCompatActivity(), BtnClickListenerShare {
    var news = NewsModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        news = Gson().fromJson<NewsModel>(intent.getStringExtra("news"), NewsModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        val ivImage = this.findViewById<ImageView>(R.id.iv_news_detail)
        val tvTitle = this.findViewById<TextView>(R.id.tv_title_news_detail)
        val tvDescription = this.findViewById<TextView>(R.id.tv_description_news_detail)
        val tvAuthor = this.findViewById<TextView>(R.id.tv_author_news_detail)
        val tvContent = this.findViewById<TextView>(R.id.tv_content_news_detail)
        val ibShareNews = this.findViewById<ImageButton>(R.id.ib_share_news_detail)

        ibShareNews.setOnClickListener {
            news.url?.let { it1 -> onBtnClick(it1, this) }
        }

        Picasso.with(this).load(news.imageUrl).into(ivImage)
        tvTitle.text = news.title
        tvDescription.text = news.description
        tvAuthor.text = getString(R.string.by).plus(" ").plus(news.author)
        tvContent.text = news.content
    }

    override fun onBtnClick(url: String, context: Context) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, url)
        sendIntent.type = "text/plain"
        context.startActivity(sendIntent)
    }
}