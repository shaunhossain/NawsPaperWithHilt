package com.shaunhossain.nawspaperwithhilt.usecase

import com.shaunhossain.nawspaperwithhilt.model.Article
import com.shaunhossain.nawspaperwithhilt.repository.DataRepository
import retrofit2.Response
import javax.inject.Inject


class ArticleDataUseCase @Inject constructor(private val dataRepository: DataRepository) {
    suspend fun getNewsList(countryCode: String,pageNumber: Int): Response<Article> {
        return dataRepository.getRepositoryData(countryCode, pageNumber)
    }

    suspend fun getAllSearchNews(query: String,pageNumber: Int): Response<Article> {
        return dataRepository.getSearchNews(query, pageNumber)
    }

}