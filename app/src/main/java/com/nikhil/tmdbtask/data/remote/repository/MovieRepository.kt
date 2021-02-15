package com.nikhil.tmdbtask.data.remote.repository

import com.nikhil.tmdbtask.data.remote.NetworkService

class MovieRepository(val networkService: NetworkService) {

    suspend fun fetchMovieDetails(argApiKey: String,argMovieId: Int) =
        networkService.fetchMovieDetails(argMovieId,argApiKey)
}