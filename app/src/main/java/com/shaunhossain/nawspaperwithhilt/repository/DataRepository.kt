package com.shaunhossain.nawspaperwithhilt.repository

import com.shaunhossain.nawspaperwithhilt.model.Article
import com.shaunhossain.nawspaperwithhilt.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getRepositoryData(countryCode: String,pageNumber: Int): Response<Article> {
        return apiService.getArticles(countryCode,pageNumber)
    }
}