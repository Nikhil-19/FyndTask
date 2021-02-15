package com.nikhil.tmdbtask.data.remote

import com.nikhil.tmdbtask.data.remote.response.MovieDetailsResponse
import com.nikhil.tmdbtask.data.remote.response.PopularMovieListResponse
import com.redmango.couroutinespractise.apicoroutine.data.remote.EndPoints
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

  @GET(EndPoints.POPULAR_MOVIE_LIST)
   fun fetchRepositories(
    @Query("api_key") apiKey:String,
    @Query("page") pageNo:Int
   ):Call<PopularMovieListResponse>


    @GET(EndPoints.MOVIE_DETAILS)
    suspend fun fetchMovieDetails(
        @Path("movie_id") movieId:Int,
        @Query("api_key") apiKey:String,
    ):MovieDetailsResponse


}