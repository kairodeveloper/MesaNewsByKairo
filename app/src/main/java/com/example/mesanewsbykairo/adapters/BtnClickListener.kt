package com.example.mesanewsbykairo.adapters

import com.example.mesanewsbykairo.models.NewsDB
import com.example.mesanewsbykairo.services.beans.NewsModel

interface BtnClickListener {
    fun onBtnClick(obj: NewsModel)
}