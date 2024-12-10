package com.gajanan.newsapp.repository

import android.util.Log
import com.gajanan.newsapp.database.NewsDatabase
import com.gajanan.newsapp.domain.models.Article
import com.gajanan.newsapp.domain.models.TopHeadlineResponse
import com.gajanan.newsapp.network.RetrofitApiInterface
import com.gajanan.newsapp.utils.ConnectivityObserver
import com.gajanan.newsapp.utils.ResultApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import retrofit2.HttpException
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

class NewsRepository
@Inject constructor(
    private val api: RetrofitApiInterface,
    db: NewsDatabase,
    private val connectivityObserver: ConnectivityObserver
) {
    private val newsDao = db.newsDao()
    private val _getTopHeadlineNews = Channel<ResultApi<TopHeadlineResponse>>()
    val getTopHeadlineResponse = _getTopHeadlineNews.receiveAsFlow()
    suspend fun getTopHeadlineNews() {
        try {
            if (!connectivityObserver.isConnected()){
                _getTopHeadlineNews.send(ResultApi.Error(Throwable("No internet connection")))
                return
            }
            _getTopHeadlineNews.send(ResultApi.Loading())
            val result = api.getTopHeadlineNews("us")
            if (result.isSuccessful) {
                Log.d(
                    TAG, "Response: ${result.body()}"
                )
                _getTopHeadlineNews.send(
                    ResultApi.Success(data = result.body())
                )
            } else {
                Log.d(
                    TAG, "Response: ${result.message()}"
                )
                _getTopHeadlineNews.send(
                    ResultApi.Error(
                        error = Throwable(result.message())
                    )
                )
            }
        } catch (e: Exception) {
            Log.d(
                TAG, "Exception: ${e.message}"
            )
            _getTopHeadlineNews.send(
                ResultApi.Error(error = e)
            )
        }catch (e:HttpException){
            Log.d(
                TAG, "HttpException: ${e.message}"
            )
            _getTopHeadlineNews.send(
                ResultApi.Error(error = e)
            )
        }
    }

    suspend fun saveArticleInDB(article: Article) {
        val articleDB = article.mapToArticleDB()
        newsDao.insertNewsArticle(articleDB)
    }

    fun getAllSavedArticles() = newsDao.getAllArticles()

    suspend fun isArticleSaved(article: Article): Boolean {
        return newsDao.isArticleSaved(article.publishedAt!!)
    }

    suspend fun deleteArticle(article: Article) {
        newsDao.deleteArticle(article.publishedAt!!)
    }

    companion object {
        const val TAG = "NewsRepository"
    }
}