package com.example.mesanewsbykairo.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.mesanewsbykairo.adapters.RecyclerViewFavoriteAdapter.ViewHolder
import androidx.recyclerview.widget.RecyclerView
import com.example.mesanewsbykairo.models.NewsDB
import com.example.mesanewsbykairo.services.beans.NewsModel
import com.example.mesanewsbykairo.ui.R
import com.orm.SugarRecord
import com.squareup.picasso.Picasso

open class RecyclerViewFavoriteAdapter(
    private var news: ArrayList<NewsDB>,
    private var context: Context,
    private var clickListener: BtnClickListenerFavorites
) : RecyclerView.Adapter<ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), BtnClickListenerShare {
        private val imageNews = itemView.findViewById<ImageView>(R.id.iv_image_news)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tv_title_news)
        private val tvDescriptionNews = itemView.findViewById<TextView>(R.id.tv_description_news)
        private val tvAuthorNews = itemView.findViewById<TextView>(R.id.tv_author_news)
        private val btnFavoriteSelected = itemView.findViewById<ImageButton>(R.id.btn_favorite_news_selected)
        private val btnFavoriteNonSelected = itemView.findViewById<ImageButton>(R.id.btn_favorite_news_non_selected)
        private val ibShareNews = itemView.findViewById<ImageButton>(R.id.ib_share_news)
        private val tvCardTitle = itemView.findViewById<TextView>(R.id.tv_card_title_favorites)

        fun bindView(news: NewsDB, context: Context, viewType: Int) {
            if (viewType==1) {
                tvCardTitle.text = context.getString(R.string.favorites_title)
            } else {
                tvTitle.text = news.title.plus("...")
                tvDescriptionNews.text = news.description.plus("...")
                tvAuthorNews.text = context.getString(R.string.by).plus(" ").plus(news.author)

                btnFavoriteSelected.visibility = View.GONE
                btnFavoriteNonSelected.visibility = View.GONE
                Picasso.with(context).load(news.imageUrl).into(imageNews)

                ibShareNews.setOnClickListener {
                    onBtnClick(news.url, context)
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

    open fun setDataOnList(listNewsModel: ArrayList<NewsDB>) {
        news.addAll(listNewsModel)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (news[position].isTitle) {
            1
        } else {
            2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 1) {
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_title_top_favorites, parent, false))
        } else {
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_news, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        val selectedItem = news[position]
        holder.bindView(selectedItem, context, viewType)
        holder.itemView.setOnClickListener {
            clickListener.onBtnClick(selectedItem)
        }
    }
}