package com.nikhil.tmdbtask.ui.wishList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nikhil.tmdbtask.data.local.db.dao.WishListDAO
import com.nikhil.tmdbtask.data.local.db.entity.ResultEntity
import com.nikhil.tmdbtask.data.remote.NetworkService
import com.nikhil.tmdbtask.data.remote.repository.MovieRepository
import com.nikhil.tmdbtask.data.remote.response.MovieDetailsResponse
import com.redmango.couroutinespractise.apicoroutine.Resource
import kotlinx.coroutines.Dispatchers


class WishListViewModel(private val wishListDAO: WishListDAO) : ViewModel() {

    fun fetchWishListDetails()= liveData<Resource<List<ResultEntity>>>(Dispatchers.IO) {
        emit(Resource.loading(null,"Fetching Details..."))
        try {
                emit(Resource.success(wishListDAO.getWishListDetails()))

        } catch (e: Exception) {
            emit(Resource.error(null, e.localizedMessage))
        }
    }
}