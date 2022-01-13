package com.demo.articlesearch.viewmodel

import android.app.Activity
import android.content.Context
import com.demo.articlesearch.BaseActivity
import com.demo.articlesearch.BaseConfig
import com.demo.articlesearch.repository.ApiRepository
import com.demo.articlesearch.retrofit.ApiInterface
import com.demo.articlesearch.retrofit.RetrofitClient
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response

class ArticleViewModel {
    companion object {

        /***********************************
         *
         * CREATE SERVICE
         *
         ***********************************/

        private fun createService(): ApiInterface {
            return RetrofitClient.getClient().create(ApiInterface::class.java)
        }

        /***********************************
         *
         * API LIST
         *
         ***********************************/

        fun getArticle(
            context: Context,
            callBack: ApiRepository.CallBack,
            query: String?,
            page: Int?,
            progress: Boolean
        ) {

            val call =
                createService().get_article_search("$query", "$page", BaseConfig.API_KEY())
            ApiRepository(context, callBack, call, "getArticle", progress)
        }

        fun getArticleViewed(
            context: Context,
            callBack: ApiRepository.CallBack,
            progress: Boolean
        ) {

            val call =
                createService().get_article_viewed(BaseConfig.API_KEY())
            ApiRepository(context, callBack, call, "getArticlePopular", progress)
        }

        fun getArticleShared(
            context: Context,
            callBack: ApiRepository.CallBack,
            progress: Boolean
        ) {

            val call =
                createService().get_article_shared(BaseConfig.API_KEY())
            ApiRepository(context, callBack, call, "getArticlePopular", progress)
        }

        fun getArticleEmailed(
            context: Context,
            callBack: ApiRepository.CallBack,
            progress: Boolean
        ) {

            val call =
                createService().get_article_emailed(BaseConfig.API_KEY())
            ApiRepository(context, callBack, call, "getArticlePopular", progress)
        }

        /***********************************
         *
         * HANDLING ERROR MESSAGE
         *
         ***********************************/

        fun onErrorResponseAPI(
            context: Context,
            activity: Activity,
            response: Response<ResponseBody>?
        ) {

            if (response?.errorBody() != null) {
                try {
                    val errorJson = response.errorBody()!!.string()
                    val error = JSONObject(errorJson)

                    if (error.has("message")) {
                        val gerError = error.getString("message")
                        BaseActivity.showToast(context, gerError)
                        return
                    }

                    if (error.has("errors")) {
                        val gerError = error.getString("errors")
                        val error = JSONArray(gerError)
                        var errorMessage = ""

                        for (i in 0 until error.length()) {
                            errorMessage = "${error[i]}"
                        }

                        if (errorMessage != "") {
                            BaseActivity.showToast(context, errorMessage)
                            return
                        }
                    }

                    if (error.has("error")) {
                        val gerError = error.getString("error")

                        try {
                            if (JSONObject(gerError).has("message")) {
                                val code = error.getString("message")
                                BaseActivity.showToast(context, code)

                                return
                            }
                        } catch (e: Exception) {
                        }
                        BaseActivity.showToast(context, gerError)
                        return
                    }
                } catch (e: Exception) {
                }
            }

            BaseActivity.showToast(context, "Failed to get response")
        }
    }
}