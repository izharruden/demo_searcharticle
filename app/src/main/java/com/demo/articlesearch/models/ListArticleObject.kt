package com.demo.articlesearch.models

import com.google.gson.annotations.SerializedName

class ListArticleObject(
    @SerializedName("title")
    val title: String?,
    @SerializedName("desc1")
    val desc1: String?,
    @SerializedName("desc2")
    val desc2: String?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("tag")
    val tag: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("pub_date")
    val pub_date: String?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("image")
    val image: String?
)