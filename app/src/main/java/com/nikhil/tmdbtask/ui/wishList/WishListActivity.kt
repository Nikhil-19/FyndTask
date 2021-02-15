package com.nikhil.tmdbtask.ui.wishList

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikhil.motialoswaltask.data.local.db.DatabaseService
import com.nikhil.tmdbtask.R
import com.nikhil.tmdbtask.data.local.db.dao.WishListDAO
import com.nikhil.tmdbtask.data.local.db.entity.ResultEntity
import com.nikhil.tmdbtask.databinding.ActivityWishListBinding
import com.nikhil.tmdbtask.ui.adapters.WishListAdapter
import com.redmango.couroutinespractise.apicoroutine.Status

class WishListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWishListBinding
    private lateinit var wishListDAO: WishListDAO
    private lateinit var viewModelFactory: WishListViewModelFactory
    private lateinit var wishListViewModel: WishListViewModel
    private lateinit var wishListAdapter: WishListAdapter
    private lateinit var wishList: MutableList<ResultEntity>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_wish_list)
        wishListDAO= DatabaseService.getDatabaseService(applicationContext).getDAO()
        viewModelFactory= WishListViewModelFactory(wishListDAO)
        wishListViewModel= ViewModelProvider(this, viewModelFactory).get(WishListViewModel::class.java)
        setAdapters();
        setObservers();
    }

    private fun setAdapters() {
       wishList= mutableListOf()
       wishListAdapter= WishListAdapter(wishList)
        binding.rvWishList.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.HORIZONTAL
            )
        )
        binding.rvWishList.apply {
            layoutManager = LinearLayoutManager(this@WishListActivity)
            adapter = wishListAdapter
        }
    }

    private fun updateWishListDetails(data: List<ResultEntity>) {
        if (wishList.isNotEmpty()) {
            wishList.clear()
        }
        wishList.addAll(data)
        wishListAdapter.notifyDataSetChanged()
    }

    private fun setObservers() {
        wishListViewModel.fetchWishListDetails().observe(this,{
            it?.let {
                 when(it.status){
                    Status.LOADING->{
                    }
                    Status.SUCCESS->{
                        if (it.data != null) {
                            updateWishListDetails(it.data)
                        } else {
                            toast(getString(R.string.error_msg))
                        }
                    }
                    Status.ERROR->{
                        toast(it?.message ?: getString(R.string.error_msg))
                    }
                    else->{
                        toast(it?.message ?: getString(R.string.error_msg))
                    }
                 }
            }
        })
    }


    private fun Context.toast(argMessage: String) {
        Toast.makeText(this, argMessage, Toast.LENGTH_SHORT).show()
    }
}