package com.shaunhossain.nawspaperwithhilt.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.shaunhossain.nawspaperwithhilt.model.Article
import com.shaunhossain.nawspaperwithhilt.model.Resource
import com.shaunhossain.nawspaperwithhilt.usecase.ArticleDataUseCase
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MainViewModel @ViewModelInject constructor(private val articleDataUseCase: ArticleDataUseCase) : ViewModel() {
    private val _breakingNews: MutableLiveData<Resource<Article>> = MutableLiveData()
    val breakingNews : LiveData<Resource<Article>>
        get() = _breakingNews
    private var breakingNewsPage = 1
    private var breakingNewsResponse: Article?= null

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        safeBreakingNewsCall(countryCode)

    }

    private suspend fun safeBreakingNewsCall(countryCode: String){
        _breakingNews.postValue(Resource.Loading())
        try {
                val response = articleDataUseCase.getNewsList(countryCode,breakingNewsPage)
                _breakingNews.postValue(handleBreakingNewsResponse(response))
        } catch (t : Throwable){
            when (t) {
                is IOException -> _breakingNews.postValue(Resource.Error("Network Failure"))
                else -> _breakingNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    private fun handleBreakingNewsResponse(resource: Response<Article>): Resource<Article>{
        if (resource.isSuccessful){
            resource.body()?.let { resultResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null){
                    breakingNewsResponse = resultResponse
                } else{
                    val oldArticle = breakingNewsResponse?.articles
                    val newArticle = resultResponse.articles
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(resource.message())
    }


}