package com.shaunhossain.nawspaperwithhilt.usecase

import com.shaunhossain.nawspaperwithhilt.model.Article
import com.shaunhossain.nawspaperwithhilt.model.Resource
import com.shaunhossain.nawspaperwithhilt.repository.DataRepository
import okio.IOException
import retrofit2.Response
import javax.inject.Inject


class ArticleDataUseCase @Inject constructor(private val dataRepository: DataRepository) {
    suspend fun getNewsList(countryCode: String,pageNumber: Int): Response<Article> {
        val getAllNews = dataRepository.getRepositoryData(countryCode, pageNumber)

       /* val resultData = try{
            if(getAllNews.isSuccessful){
                Resource.Success(getAllNews)
            }else{
                Resource.Error("Service not found")
            }
        }catch(t : Throwable ){
            when(t){
                is IOException -> Resource.Exception("Network fail")
                else -> Resource.Exception("Internal Problem")
            }

        }*/

        return getAllNews
    }

}