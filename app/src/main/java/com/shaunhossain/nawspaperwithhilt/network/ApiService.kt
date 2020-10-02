package com.shaunhossain.nawspaperwithhilt.network

import com.shaunhossain.nawspaperwithhilt.model.Article
import com.shaunhossain.nawspaperwithhilt.utlis.Constant.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/top-headlines")
    suspend fun getArticles(
        @Query("country")
        country : String = "us",
        @Query("page")
        page : Int ,
        @Query("apiKey")
        apiKey : String = API_KEY
    ): Response<Article>

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q")
        searchQuery: String ,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<Article>
}