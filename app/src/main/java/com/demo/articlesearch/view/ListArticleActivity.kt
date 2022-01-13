package com.demo.articlesearch.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.articlesearch.BaseActivity
import com.demo.articlesearch.R
import com.demo.articlesearch.adapter.ListArticleAdapter
import com.demo.articlesearch.models.ListArticleData
import com.demo.articlesearch.models.ListArticleObject
import com.demo.articlesearch.repository.ApiRepository
import com.demo.articlesearch.viewmodel.ArticleViewModel
import com.google.gson.GsonBuilder
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_list_article.*
import okhttp3.ResponseBody
import retrofit2.Response

class ListArticleActivity : BaseActivity(), ApiRepository.CallBack {

    companion object {
        var search = false
        var searchInput = ""
        var viewed = false
        var shared = false
        var emailed = false
    }

    private var listAdapter: ListArticleAdapter? = null
    private var listObject = ArrayList<ListArticleObject>()
    private var listPage = 0
    private var loadingPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_article)
        StatusBarUtil.setTranslucentForImageView(this, 20, null)


        when {
            search -> centerTitle.text = "Articles"
            viewed -> centerTitle.text = "Most Viewed Articles"
            shared -> centerTitle.text = "Most Shared Articles"
            emailed -> centerTitle.text = "Most Emailed Articles"
        }
        leftIcon.setOnClickListener { onBackPressed() }
        initList()
    }

    private fun initList() {
        listObject.clear()

        listAdapter = ListArticleAdapter(listObject)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = listAdapter
        recyclerView.isNestedScrollingEnabled = false

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (loadingPage) {
                    loadingPage = false
                    listPage += 1
                    if (search) callListAPI()
                }
            }
        })

        callListAPI()
    }

    private fun callListAPI() {
        when {
            search -> ArticleViewModel.getArticle(this, this, searchInput, listPage, true)
            viewed -> ArticleViewModel.getArticleViewed(this, this, true)
            shared -> ArticleViewModel.getArticleShared(this, this, true)
            emailed -> ArticleViewModel.getArticleEmailed(this, this, true)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResponseAPI(response: Response<ResponseBody>?, api: String, success: Boolean) {
        when {
            success -> {
                val gson = GsonBuilder().create()
                val getResponseString = response!!.body()!!.string()

                if (api == "getArticle") {
                    loadingPage = false

                    val getResponse = gson.fromJson(getResponseString, ListArticleData::class.java)
                    if (getResponse.response?.docs != null) {
                        (getResponse.response.docs.indices).forEach { i ->
                            val getDocs = getResponse.response.docs[i]
                            var getMedia = ""
                            if (getDocs.multimedia != null && getDocs.multimedia.isNotEmpty()) {
                                (getDocs.multimedia.indices).forEach { m ->
                                    if (getMedia == "") getMedia =
                                        "https://static01.nyt.com/" + getDocs.multimedia[m].url
                                }
                            }
                            listObject.add(
                                ListArticleObject(
                                    getDocs.headline?.main,
                                    getDocs.snippet,
                                    getDocs.lead_paragraph,
                                    getDocs.byline?.original,
                                    getDocs.section_name,
                                    null,
                                    getDocs.pub_date,
                                    getDocs.web_url,
                                    getMedia
                                )
                            )
                        }

                        if (getResponse.response.meta?.offset != null
                            && getResponse.response.meta.hits != null
                            && getResponse.response.meta.hits > getResponse.response.meta.offset
                        ) {
                            loadingPage = true
                        }

                        listAdapter?.updateModel(listObject)
                        listAdapter?.notifyDataSetChanged()
                    }
                }

                if (api == "getArticlePopular") {
                    loadingPage = false

                    val getResponse = gson.fromJson(getResponseString, ListArticleData::class.java)
                    if (getResponse.results != null) {
                        (getResponse.results.indices).forEach { i ->
                            val getResult = getResponse.results[i]

                            var getMedia = ""
                            var getMediaHeight: Long = 0
                            if (getResult.media != null && getResult.media.isNotEmpty()) {
                                (getResult.media.indices).forEach { m ->
                                    if (getResult.media[m].type == "image") {
                                        val media = getResult.media[m]
                                        if (media.metadata != null && media.metadata.isNotEmpty()) {
                                            (media.metadata.indices).forEach { d ->
                                                if (media.metadata[d].height != null && getMediaHeight < media.metadata[d].height!!) {
                                                    getMedia = "${media.metadata[d].url}"
                                                    getMediaHeight = media.metadata[d].height!!
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            listObject.add(
                                ListArticleObject(
                                    getResult.title,
                                    getResult.abstract,
                                    "",
                                    getResult.byline,
                                    getResult.section,
                                    getResult.published_date,
                                    null,
                                    getResult.url,
                                    getMedia
                                )
                            )
                        }

                        listAdapter?.updateModel(listObject)
                        listAdapter?.notifyDataSetChanged()
                    }
                }
            }
            else -> ArticleViewModel.onErrorResponseAPI(this, this, response)
        }
    }
}