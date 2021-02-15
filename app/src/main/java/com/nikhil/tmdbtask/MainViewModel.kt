package com.nikhil.tmdbtask
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.nikhil.tmdbtask.data.local.db.dao.WishListDAO
import com.nikhil.tmdbtask.data.local.db.entity.ResultEntity
import com.nikhil.tmdbtask.data.remote.NetworkService
import com.nikhil.tmdbtask.data.remote.repository.MovieRepository
import com.nikhil.tmdbtask.data.remote.response.Result
import com.nikhil.tmdbtask.paging.MovieDataSourceFactory
import com.nikhil.tmdbtask.paging.MovieListDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors


class MainViewModel(private val networkService: NetworkService, private val wishListDAO: WishListDAO) : ViewModel() {
    private val movieRepository = MovieRepository(networkService)
     lateinit var itemPageList:LiveData<PagedList<Result>>
    private lateinit var movieListDataSourceLiveData: LiveData<MovieListDataSource>
    private var wishListCount:Int=0
    private  var isMovieAddedtoWishList=MutableLiveData<Boolean>(false)

     fun setUp(){
       val movieDataSourceFactory= MovieDataSourceFactory(networkService)
        movieListDataSourceLiveData=movieDataSourceFactory.getMovieListDataSourceLiveData()
        val executor = Executors.newFixedThreadPool(5);

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(30)
            .build()
        itemPageList=LivePagedListBuilder<Int,Result>(movieDataSourceFactory,config)
                              .setFetchExecutor(executor)
                              .build()
    }


    fun addToWishList(result: Result)=viewModelScope.launch(Dispatchers.IO) {
        val resultEntity=ResultEntity(
               null,
                result.adult,
                result.backdropPath,
                result.id,
                result.originalLanguage,
                result.originalTitle,
                result.overview,
                result.popularity,
                result.posterPath,
                result.releaseDate,
                result.title,
                result.video,
                result.voteAverage,
                result.voteCount
        )

        val insertionId=wishListDAO.insert(resultEntity)
        if(insertionId>0)
        {
            isMovieAddedtoWishList.postValue(true)
        }
        Log.d("Inserted Id",insertionId.toString())
    }


    fun getWishListCount()= liveData<Int>(Dispatchers.IO) {
        this.emit(wishListDAO.getWishListCount())
    }


    fun getIsMovieAddedToWishList()=isMovieAddedtoWishList


}