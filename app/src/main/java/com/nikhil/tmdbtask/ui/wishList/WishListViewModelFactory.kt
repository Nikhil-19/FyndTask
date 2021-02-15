package com.nikhil.tmdbtask.ui.wishList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikhil.tmdbtask.data.local.db.dao.WishListDAO
import com.nikhil.tmdbtask.data.remote.NetworkService
import java.lang.IllegalArgumentException

class WishListViewModelFactory(private val wishListDAO: WishListDAO):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if(modelClass.isAssignableFrom(WishListViewModel::class.java))
       {
           return WishListViewModel(wishListDAO) as T
       }
       else{
           throw IllegalArgumentException("Unknown View Model")
       }
    }
}