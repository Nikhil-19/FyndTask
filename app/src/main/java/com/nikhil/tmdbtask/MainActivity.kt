package com.nikhil.tmdbtask

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikhil.motialoswaltask.data.local.db.DatabaseService
import com.nikhil.motialoswaltask.utility.Constants
import com.nikhil.tmdbtask.data.local.db.dao.WishListDAO
import com.nikhil.tmdbtask.data.remote.response.Result
import com.nikhil.tmdbtask.databinding.ActivityMainBinding
import com.nikhil.tmdbtask.ui.adapters.MoviesPageListAdapter
import com.nikhil.tmdbtask.ui.adapters.WishListAdapter
import com.nikhil.tmdbtask.ui.movieDetails.MovieDetailsActivity
import com.nikhil.tmdbtask.ui.wishList.WishListActivity
import com.redmango.couroutinespractise.apicoroutine.data.remote.Networking
import java.lang.String

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var textViewWishList: TextView
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var mainViewModel: MainViewModel
    private lateinit var movieListAdapter: MoviesPageListAdapter
    private lateinit var wishListDAO: WishListDAO
    private  var wishListCount:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_main)
        wishListDAO= DatabaseService.getDatabaseService(applicationContext).getDAO()
        viewModelFactory= MainViewModelFactory(Networking.create(), wishListDAO)
        mainViewModel=ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        setObservers()
    }


    private fun setAdapters(pagedList: PagedList<Result>) {
        movieListAdapter = MoviesPageListAdapter()
        movieListAdapter.submitList(pagedList)

        binding.rvMovieList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieListAdapter
        }

        movieListAdapter.setOnMovieItemClickListener(object :
            MoviesPageListAdapter.OnMovieItemClickListener {
            override fun onMovieItemClick(movieItem: Result) {
                Log.d("Main Activity", movieItem.originalTitle)
                navigateToMovieDetails(movieItem.id)
            }
        })

        movieListAdapter.setOnWishClickListener(object :
            MoviesPageListAdapter.OnWishListClickListener {
            override fun onWishListClick(movieItem: Result) {
                Log.d("Main Activity Wish List", movieItem.originalTitle)
                showAlertDialog(movieItem)
            }

        })

    }

    private fun showAlertDialog(movieItem: Result) {

        AlertDialog.Builder(this).apply {
            setMessage(getString(R.string.wish_list_message, movieItem.title))
            setPositiveButton(getString(R.string.label_yes_btn)) { dialogInterface: DialogInterface, i: Int ->
                mainViewModel.addToWishList(movieItem)
            }
            setNegativeButton(
                getString(R.string.label_no_btn)
            ) { p0, p1 -> p0?.dismiss() }
            show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.main_menu, menu)
       val menuItem: MenuItem? =menu?.findItem(R.id.action_wish_list)
       val actionView: View? =menuItem?.actionView
       textViewWishList= actionView?.findViewById(R.id.cart_badge)as TextView
       setupBadge()

       actionView?.setOnClickListener {
            onOptionsItemSelected(menuItem)
       }
        return super.onCreateOptionsMenu(menu)
    }


    private fun setupBadge() {
        if (wishListCount == 0) {
            if (textViewWishList.visibility != View.GONE) {
                textViewWishList.visibility = View.GONE
            }
        } else {
            textViewWishList.visibility=View.VISIBLE
            textViewWishList.text = wishListCount.toString()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.action_wish_list -> {
                navigateToWishListDetails();
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToWishListDetails() {
        Intent(this, WishListActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun setObservers() {

        mainViewModel.setUp()
        mainViewModel.itemPageList.observe(this, {
            it?.let {
                Log.d("Main Activity ", it.toString())
                setAdapters(it)
            }
        })

        mainViewModel.getWishListCount().observe(this,{
            wishListCount=it?:0
        })

        mainViewModel.getIsMovieAddedToWishList().observe(this,{
            it?.let {
                when(it)
                {
                    true->
                    {
                      toast("Movie Added To Wish List SucessFully")
                      invalidateOptionsMenu()
                        wishListCount++
                        setupBadge()
                    }
                }
            }
        })



    }


    private fun navigateToMovieDetails(id: Int) {
        Intent(this, MovieDetailsActivity::class.java).apply {
            putExtra(Constants.INTENT_KEY_MOVIE_ID, id)
            startActivity(this)
        }

    }

    private fun Context.toast(argMessage: kotlin.String) {
        Toast.makeText(this, argMessage, Toast.LENGTH_SHORT).show()
    }



}