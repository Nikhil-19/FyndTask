package com.nikhil.tmdbtask.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nikhil.tmdbtask.R
import com.nikhil.tmdbtask.data.remote.response.Result
import com.nikhil.tmdbtask.databinding.HolderMovieItemBinding

class MoviesPageListAdapter(): PagedListAdapter<Result, MoviesPageListAdapter.MyViewHolder>(Result.Callback) {
  private  var listener:OnMovieItemClickListener?=null
  private  var wishClickListener:OnWishListClickListener?=null
  inner class MyViewHolder(val itemBinding: HolderMovieItemBinding) :RecyclerView.ViewHolder(itemBinding.root){

      fun bindMovieItem(movieItem: Result?, imageUrl: String) {
          itemBinding.movieData=movieItem
          itemBinding.imageUrl=imageUrl
          itemBinding.executePendingBindings()

      }

  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val itemBinding: HolderMovieItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.holder_movie_item,parent,false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movieItem=getItem(position)
        val imageUrl="https://image.tmdb.org/t/p/w500"+movieItem?.posterPath
        holder.bindMovieItem(movieItem,imageUrl)

        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> listener?.onMovieItemClick(it1) }
        }

        holder.itemBinding.btnWishList.setOnClickListener {
            getItem(position)?.let { it1 -> wishClickListener?.onWishListClick(it1) }
        }


    }

    fun setOnMovieItemClickListener(param: OnMovieItemClickListener) {
         listener=param
    }

    fun setOnWishClickListener(param: OnWishListClickListener) {
        wishClickListener=param
    }

    interface OnMovieItemClickListener{
        fun onMovieItemClick(movieItem: Result)
    }


    interface OnWishListClickListener{
        fun onWishListClick(movieItem: Result)
    }

}