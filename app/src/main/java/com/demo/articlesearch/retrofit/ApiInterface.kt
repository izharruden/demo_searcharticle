package com.demo.articlesearch.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("svc/search/v2/articlesearch.json")
    fun get_article_search(
        @Query("q") query: String,
        @Query("page") page: String,
        @Query("api-key") key: String
    ): Call<ResponseBody>

    @GET("svc/mostpopular/v2/viewed/1.json")
    fun get_article_viewed(
        @Query("api-key") key: String
    ): Call<ResponseBody>

    @GET("svc/mostpopular/v2/shared/1.json")
    fun get_article_shared(
        @Query("api-key") key: String
    ): Call<ResponseBody>

    @GET("svc/mostpopular/v2/emailed/1.json")
    fun get_article_emailed(
        @Query("api-key") key: String
    ): Call<ResponseBody>
}