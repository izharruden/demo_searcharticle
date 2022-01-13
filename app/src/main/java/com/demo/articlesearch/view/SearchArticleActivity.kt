package com.demo.articlesearch.view

import android.content.Intent
import android.os.Bundle
import com.demo.articlesearch.BaseActivity
import com.demo.articlesearch.R
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_search_article.*

class SearchArticleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_article)
        StatusBarUtil.setTranslucentForImageView(this, 20, null)

        centerTitle.text = "Search Article"

        leftIcon.setOnClickListener {
            onBackPressed()
        }

        searchButton.setOnClickListener {

            ListArticleActivity.search = true
            ListArticleActivity.searchInput = searchFilterEditText.text.toString()
            ListArticleActivity.viewed = false
            ListArticleActivity.shared = false
            ListArticleActivity.emailed = false
            val i = Intent(this, ListArticleActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }
    }
}