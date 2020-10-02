package com.shaunhossain.nawspaperwithhilt.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.shaunhossain.nawspaperwithhilt.model.Article
import com.shaunhossain.nawspaperwithhilt.model.Resource
import com.shaunhossain.nawspaperwithhilt.usecase.ArticleDataUseCase
import com.shaunhossain.nawspaperwithhilt.utlis.InternetChecker
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException

class MainViewModel @ViewModelInject constructor(private val articleDataUseCase: ArticleDataUseCase,
                                                 private val internetChecker: InternetChecker) : ViewModel() {
    //For News Page
    private val _breakingNews: MutableLiveData<Resource<Article>> = MutableLiveData()
    val breakingNews : LiveData<Resource<Article>>
        get() = _breakingNews
    var breakingNewsPage = 1
    private var breakingNewsResponse: Article?= null

    //For Search News
    private val _searchNews: MutableLiveData<Resource<Article>> = MutableLiveData()
    val searchNews : LiveData<Resource<Article>>
        get() = _searchNews
    var searchNewsPage = 1
    private var searchNewsResponse: Article?= null

    private val viewModelJobNews = Job()
    private val viewModelJobSearchNews = Job()
    private val uiScopeNews = CoroutineScope(Dispatchers.Main + viewModelJobNews)
    private val uiScopeSearchNews = CoroutineScope(Dispatchers.Main + viewModelJobSearchNews)

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String){
        uiScopeNews.launch {
           fetchNewsforServer(countryCode)
        }
    }

    fun searchNews(searchQuery: String){
        uiScopeSearchNews.launch {
            fetchSearchNewsFromServer(searchQuery)
        }
    }

    private suspend fun fetchNewsforServer(countryCode: String){
        withContext(Dispatchers.IO){
            safeBreakingNewsCall(countryCode)
        }
    }

    private suspend fun fetchSearchNewsFromServer(searchQuery: String){
        withContext(Dispatchers.IO){
            safeSearchNews(searchQuery)
        }
    }
    private suspend fun safeBreakingNewsCall(countryCode: String){
        _breakingNews.postValue(Resource.Loading())
        try {
            if(internetChecker.isNetworkConnected()){
                val response = articleDataUseCase.getNewsList(countryCode,breakingNewsPage)
                _breakingNews.postValue(handleBreakingNewsResponse(response))
            } else {
                _breakingNews.postValue(Resource.Error("No internet connection"))
            }
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

    private fun handleSearchNewsResponse (resource: Response<Article>): Resource<Article>{
        if (resource.isSuccessful){
            resource.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null){
                    searchNewsResponse = resultResponse
                } else{
                    val oldArticle = searchNewsResponse?.articles
                    val newArticle = resultResponse.articles
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(resource.message())
    }

    private suspend fun safeSearchNews(searchQuery: String){
        _searchNews.postValue(Resource.Loading())
        try {
            if(internetChecker.isNetworkConnected()){
                val response = articleDataUseCase.getAllSearchNews(searchQuery,searchNewsPage)
                _searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                _searchNews.postValue(Resource.Error("No internet connection"))
            }
        } catch (t : Throwable){
            when (t) {
                is IOException -> _searchNews.postValue(Resource.Error("Network Failure"))
                else -> _searchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJobNews.complete()
        viewModelJobSearchNews.complete()
    }


}