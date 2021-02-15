package com.nikhil.tmdbtask.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.nikhil.tmdbtask.data.remote.NetworkService
import com.nikhil.tmdbtask.data.remote.response.PopularMovieListResponse
import com.nikhil.tmdbtask.data.remote.response.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListDataSource(private val networkService: NetworkService) :
    PageKeyedDataSource<Int, Result>() {

    private val FIRST_PAGE: Int = 1
    private val API_KEY: String = "dfaf5827b1adb11c56e5a241953146f8"

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) {
        Log.d("MovieListDataSource", "loadInitial")
        networkService.fetchRepositories(API_KEY, FIRST_PAGE)
            .enqueue(object : Callback<PopularMovieListResponse> {
                override fun onResponse(
                    call: Call<PopularMovieListResponse>,
                    response: Response<PopularMovieListResponse>
                ) {
                    if (response.isSuccessful && response.code() == 200) {

                        response.body()?.results?.let {
                            callback.onResult(
                                it,
                                null,
                                FIRST_PAGE + 1
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<PopularMovieListResponse>, t: Throwable) {
                    Log.d("MovieListDataSource", "loadInitial On Failure")
                }

            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        Log.d("MovieListDataSource", "loadBefore")
        networkService.fetchRepositories(API_KEY, params.key)
            .enqueue(object : Callback<PopularMovieListResponse> {
                override fun onResponse(
                    call: Call<PopularMovieListResponse>,
                    response: Response<PopularMovieListResponse>
                ) {
                    if (response.isSuccessful && response.code() == 200) {
                        val key:Int?=when {
                            params.key> 1 -> params.key-1
                            else -> null
                        }
                        response.body()?.results?.let {
                            callback.onResult(
                                it,
                                key,
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<PopularMovieListResponse>, t: Throwable) {
                    Log.d("MovieListDataSource", "loadInitial On Failure")
                }

            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        Log.d("MovieListDataSource", "loadAfter")
        networkService.fetchRepositories(API_KEY, params.key)
            .enqueue(object : Callback<PopularMovieListResponse> {
                override fun onResponse(
                    call: Call<PopularMovieListResponse>,
                    response: Response<PopularMovieListResponse>
                ) {
                    if (response.isSuccessful && response.code() == 200) {

                        val key:Int?= when (params.key) {
                            response.body()?.totalPages -> params.key-1
                            else -> params.key+1
                        }

                        response.body()?.results?.let {
                            callback.onResult(
                                it,
                                key
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<PopularMovieListResponse>, t: Throwable) {
                    Log.d("MovieListDataSource", "loadInitial On Failure")
                }

            })
    }
}