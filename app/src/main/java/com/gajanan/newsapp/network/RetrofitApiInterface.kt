package com.gajanan.newsapp.network

import com.gajanan.newsapp.domain.models.TopHeadlineResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiInterface {
    @GET(Endpoints.TOP_HEADLINE_NEWS)
    suspend fun getTopHeadlineNews(
        @Query("country") country: String = "us"
    ) : Response<TopHeadlineResponse>

//    @GET("${Endpoints.MOVIE}/{movie_id}")
//    suspend fun getMovieDetails(
//        @Path("movie_id") movieId:String
//    ) : Response<MovieDetailResponse>
}