package com.demo.articlesearch.view

import android.content.Intent
import android.os.Bundle
import com.demo.articlesearch.BaseActivity
import com.demo.articlesearch.R
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.setTranslucentForImageView(this, 20, null)

        centerTitle.text = "NYTimes"

        searchTab.setOnClickListener {
            val i = Intent(this, SearchArticleActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }

        allTab.setOnClickListener {

            ListArticleActivity.search = true
            ListArticleActivity.searchInput = ""
            ListArticleActivity.viewed = false
            ListArticleActivity.shared = false
            ListArticleActivity.emailed = false
            val i = Intent(this, ListArticleActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }

        mostViewedTab.setOnClickListener {
            ListArticleActivity.search = false
            ListArticleActivity.searchInput = ""
            ListArticleActivity.viewed = true
            ListArticleActivity.shared = false
            ListArticleActivity.emailed = false
            val i = Intent(this, ListArticleActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }

        mostSharedTab.setOnClickListener {
            ListArticleActivity.search = false
            ListArticleActivity.searchInput = ""
            ListArticleActivity.viewed = false
            ListArticleActivity.shared = true
            ListArticleActivity.emailed = false
            val i = Intent(this, ListArticleActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }

        mostEmailedTab.setOnClickListener {
            ListArticleActivity.search = false
            ListArticleActivity.searchInput = ""
            ListArticleActivity.viewed = false
            ListArticleActivity.shared = false
            ListArticleActivity.emailed = true
            val i = Intent(this, ListArticleActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }
    }
}