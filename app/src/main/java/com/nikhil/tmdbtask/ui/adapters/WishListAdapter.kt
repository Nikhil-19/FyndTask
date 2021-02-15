package com.nikhil.tmdbtask.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nikhil.tmdbtask.R
import com.nikhil.tmdbtask.data.local.db.entity.ResultEntity
import com.nikhil.tmdbtask.data.remote.response.Result
import com.nikhil.tmdbtask.databinding.HolderMovieItemBinding
import com.nikhil.tmdbtask.databinding.HolderWishListItemBinding

class WishListAdapter(private val movieList: List<ResultEntity>): RecyclerView.Adapter<WishListAdapter.WishListViewHolder>() {

  inner class WishListViewHolder(private val itemBinding: HolderWishListItemBinding) :RecyclerView.ViewHolder(itemBinding.root){

      fun bindMovieItem(movieItem: ResultEntity) {
          itemBinding.movieData=movieItem
          itemBinding.imageUrl="https://image.tmdb.org/t/p/w500"+movieItem.posterPath
          itemBinding.executePendingBindings()
      }
  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
       val layoutInflater=LayoutInflater.from(parent.context)
       val itemBinding:HolderWishListItemBinding=DataBindingUtil.inflate(layoutInflater, R.layout.holder_wish_list_item,parent,false)
       return WishListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        val movieItem=movieList[position]
        holder.bindMovieItem(movieItem)
    }

    override fun getItemCount(): Int {
       return movieList.size
    }


}