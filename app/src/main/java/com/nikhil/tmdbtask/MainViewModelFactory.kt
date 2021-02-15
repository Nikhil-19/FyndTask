package com.nikhil.tmdbtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikhil.tmdbtask.data.local.db.dao.WishListDAO
import com.nikhil.tmdbtask.data.remote.NetworkService
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val networkService: NetworkService,private val wishListDAO: WishListDAO):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if(modelClass.isAssignableFrom(MainViewModel::class.java))
       {
           return MainViewModel(networkService,wishListDAO) as T
       }
       else{
           throw IllegalArgumentException("Unknown View Model")
       }
    }
}