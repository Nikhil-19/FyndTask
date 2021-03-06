package com.nikhil.tmdbtask.data.remote.response

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName
import java.text.DateFormat
import java.text.SimpleDateFormat

data class Result(
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @SerializedName("genre_ids")
    var genreIds: List<Int>,
    @SerializedName("id")
    var id: Int,
    @SerializedName("original_language")
    var originalLanguage: String,
    @SerializedName("original_title")
    var originalTitle: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("video")
    var video: Boolean,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("vote_count")
    var voteCount: Int
){

    companion object{
        @JvmStatic
        @BindingAdapter("profileImage")
        fun loadImage(view: ImageView, imageUrl: String) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }

        val Callback:DiffUtil.ItemCallback<Result> =object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
               return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                 return oldItem.equals(newItem)
            }

        }
    }

    fun setDate(inputDate: String):String {
        return try{
            val inputDateFormat:DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputDateFormat :DateFormat= SimpleDateFormat("dd MMM yyyy")
            outputDateFormat.format(inputDateFormat.parse(inputDate))
        } catch (e:Exception) {
            inputDate
        }

    }


}