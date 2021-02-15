package com.nikhil.tmdbtask.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.nikhil.tmdbtask.data.remote.NetworkService
import com.nikhil.tmdbtask.data.remote.response.Result

class MovieDataSourceFactory(private val networkService: NetworkService) : DataSource.Factory<Int, Result>() {

      private val mutableLiveDataMovieListDataSource=MutableLiveData<MovieListDataSource>()
      private lateinit var movieListDataSource: MovieListDataSource

    override fun create(): DataSource<Int, Result> {
        movieListDataSource=MovieListDataSource(networkService)
        mutableLiveDataMovieListDataSource.postValue(movieListDataSource)
        return  movieListDataSource
    }

    fun getMovieListDataSourceLiveData()=mutableLiveDataMovieListDataSource
}