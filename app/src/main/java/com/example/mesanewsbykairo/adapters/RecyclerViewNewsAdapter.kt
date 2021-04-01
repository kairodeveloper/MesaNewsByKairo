package com.example.mesanewsbykairo.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.mesanewsbykairo.adapters.RecyclerViewNewsAdapter.ViewHolder
import com.example.mesanewsbykairo.models.NewsDB
import com.example.mesanewsbykairo.services.beans.NewsModel
import com.example.mesanewsbykairo.ui.NewsDetailActivity
import com.example.mesanewsbykairo.ui.R
import com.google.gson.Gson
import com.orm.SugarRecord
import com.squareup.picasso.Picasso


open class RecyclerViewNewsAdapter (private var news: ArrayList<NewsModel>,
                                    private var highlights: ArrayList<NewsModel>,
                                    private var context: Context,
                                    private var clickListener: BtnClickListener) : RecyclerView.Adapter<ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), BtnClickListenerShare {
        private val imageNews = itemView.findViewById<ImageView>(R.id.iv_image_news)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tv_title_news)
        private val tvDescriptionNews = itemView.findViewById<TextView>(R.id.tv_description_news)
        private val tvAuthorNews = itemView.findViewById<TextView>(R.id.tv_author_news)
        private val btnFavoriteSelected = itemView.findViewById<ImageButton>(R.id.btn_favorite_news_selected)
        private val btnFavoriteNonSelected = itemView.findViewById<ImageButton>(R.id.btn_favorite_news_non_selected)
        private val ibShareNews = itemView.findViewById<ImageButton>(R.id.ib_share_news)

        private val llHighlights = itemView.findViewById<LinearLayout>(R.id.ll_highlights)
        private val tvCardTitle = itemView.findViewById<TextView>(R.id.tv_card_title)

        private var newsDB = NewsDB()

        private fun manipulateFavorites(news: NewsModel, newsList: List<NewsDB>, context: Context, isFavorite: Boolean) {

            if (isFavorite) {
                btnFavoriteSelected.visibility = View.VISIBLE
                btnFavoriteNonSelected.visibility = View.GONE

                newsDB = NewsDB(news.title!!, news.description!!, news.content!!, news.author!!, news.publishedAt!!, news.highlight!!, news.url!!,
                    imageUrl = news.imageUrl!!.toString(),
                    isFavorite = true,
                    isTitle = false
                )
                newsDB.save()
            } else {
                btnFavoriteSelected.visibility = View.GONE
                btnFavoriteNonSelected.visibility = View.VISIBLE

                if (newsList.isNotEmpty()) {
                    SugarRecord.deleteAll(NewsDB::class.java, "url = ?", news.url!!)
                }
            }
        }

        fun bindView(news: NewsModel, highlights: ArrayList<NewsModel>, context: Context, viewType: Int) {
            if (viewType==1) {

                var isFavorite = SugarRecord.find(NewsDB::class.java, "url = ?", news.url!!).size > 0

                btnFavoriteSelected.setOnClickListener {
                    val newsList = SugarRecord.find(NewsDB::class.java, "url = ?", news.url!!)
                    if (newsList.size>0) {
                        isFavorite = newsList[0].isFavorite
                        newsDB = newsList[0]
                    }
                    isFavorite = !isFavorite

                    manipulateFavorites(news, newsList, context, isFavorite)
                }

                btnFavoriteNonSelected.setOnClickListener {
                    val newsList = SugarRecord.find(NewsDB::class.java, "url = ?", news.url!!)
                    if (newsList.size>0) {
                        isFavorite = newsList[0].isFavorite
                        newsDB = newsList[0]
                    }
                    isFavorite = !isFavorite
                    manipulateFavorites(news, newsList, context, isFavorite)
                }

                ibShareNews.setOnClickListener {
                    onBtnClick(news.url, context)
                }

                tvTitle.text = news.title.plus("...")
                tvDescriptionNews.text = news.description.plus("...")
                tvAuthorNews.text = context.getString(R.string.by).plus(" ").plus(news.author)

                if (isFavorite) {
                    btnFavoriteSelected.visibility = View.VISIBLE
                    btnFavoriteNonSelected.visibility = View.GONE
                } else {
                    btnFavoriteSelected.visibility = View.GONE
                    btnFavoriteNonSelected.visibility = View.VISIBLE
                }

                if (news.imageUrl!=null) {
                    Picasso.with(context).load(news.imageUrl).into(imageNews)
                }
            }
            else if (viewType==2) {
                highlights.map {highlight ->
                    val child: View = LayoutInflater.from(context).inflate(R.layout.card_highlights, null)
                    val tvTitleHighlight = child.findViewById<TextView>(R.id.tv_title_highlight)
                    val ivHighlight = child.findViewById<ImageView>(R.id.iv_highlight)

                    child.setOnClickListener {
                        val intent = Intent(context, NewsDetailActivity::class.java)
                        intent.putExtra("news", Gson().toJson(highlight))
                        context.startActivity(intent)
                    }

                    tvTitleHighlight.text = highlight.title
                    Picasso.with(context).load(highlight.imageUrl).into(ivHighlight)
                    tvCardTitle.text = context.getString(R.string.news_title)

                    if (highlight.isANew) {
                        llHighlights.addView(child)
                    }
                }
            }
        }

        override fun onBtnClick(url: String, context: Context) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, url)
            sendIntent.type = "text/plain"
            context.startActivity(sendIntent)
        }
    }

    open fun setDataOnList(listNewsModel: ArrayList<NewsModel>) {
        news.addAll(listNewsModel)
        notifyDataSetChanged()
    }

    open fun setDataOnHighlight(listNewsModel: ArrayList<NewsModel>) {
        highlights.addAll(listNewsModel)
        notifyDataSetChanged()
    }

    open fun removeButtonMoreItems() {
        if (news.size>0) {
            news.removeAt(news.size-1)
            notifyItemRemoved(news.size-1)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            news[position].isANew -> {
                1
            }
            news[position].isTitle -> {
                2
            }
            else -> {
                3
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            1 -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_news, parent, false))
            }
            2 -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_title_top, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.btn_more_news, parent, false))
            }
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        val selectedItem = news[position]
        holder.bindView(selectedItem, highlights, context, viewType)
        holder.itemView.setOnClickListener {
            clickListener.onBtnClick(selectedItem)
        }
    }

}