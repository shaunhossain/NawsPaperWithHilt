package com.shaunhossain.nawspaperwithhilt.model


import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("articles")
    val articles: MutableList<ArticleX>,
    @SerializedName("status")
    val status: String = "",
    @SerializedName("totalResults")
    val totalResults: Int = 0
)