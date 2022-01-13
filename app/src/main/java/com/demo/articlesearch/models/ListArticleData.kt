package com.demo.articlesearch.models

import com.google.gson.annotations.SerializedName

data class ListArticleData(
    @SerializedName("response")
    val response: response?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("results")
    val results: List<results>?
)

data class response(
    @SerializedName("docs")
    val docs: List<docs>?,
    @SerializedName("meta")
    val meta: meta?
)

data class results(
    @SerializedName("url")
    val url: String?,
    @SerializedName("section")
    val section: String?,
    @SerializedName("published_date")
    val published_date: String?,
    @SerializedName("byline")
    val byline: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("abstract")
    val abstract: String?,
    @SerializedName("media")
    val media: List<media>?

)

data class ListData(
    @SerializedName("docs")
    val docs: docs
)

data class docs(
    @SerializedName("abstract")
    val abstract: String?,
    @SerializedName("web_url")
    val web_url: String?,
    @SerializedName("source")
    val source: String?,
    @SerializedName("type_of_material")
    val type_of_material: String?,
    @SerializedName("byline")
    val byline: byline?,
    @SerializedName("section_name")
    val section_name: String?,
    @SerializedName("pub_date")
    val pub_date: String?,
    @SerializedName("headline")
    val headline: headline?,
    @SerializedName("snippet")
    val snippet: String?,
    @SerializedName("lead_paragraph")
    val lead_paragraph: String?,
    @SerializedName("multimedia")
    val multimedia: List<multimedia>?

)

data class byline(
    @SerializedName("original")
    val original: String?
)

data class headline(
    @SerializedName("main")
    val main: String?
)

data class multimedia(
    @SerializedName("url")
    val url: String?,
    @SerializedName("type")
    val type: String?
)

data class media(
    @SerializedName("media-metadata")
    val metadata: List<metadata>?,
    @SerializedName("type")
    val type: String?
)

data class metadata(
    @SerializedName("url")
    val url: String?,
    @SerializedName("height")
    val height: Long?
)

data class meta(
    @SerializedName("hits")
    val hits: Long?,
    @SerializedName("offset")
    val offset: Long?
)