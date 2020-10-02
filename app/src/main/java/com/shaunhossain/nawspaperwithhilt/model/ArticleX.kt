package com.shaunhossain.nawspaperwithhilt.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArticleX(
    @SerializedName("author")
    val author: Any? = null,
    @SerializedName("content")
    val content: Any? = null,
    @SerializedName("description")
    val description: Any? = null,
    @SerializedName("publishedAt")
    val publishedAt: String = "",
    @SerializedName("source")
    val source: Source = Source(),
    @SerializedName("title")
    val title: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("urlToImage")
    val urlToImage: Any? = null
): Serializable