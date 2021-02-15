package com.nikhil.tmdbtask.data.local.db.entity

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.nikhil.tmdbtask.data.remote.response.Result
import java.text.DateFormat
import java.text.SimpleDateFormat


@Entity(tableName = "wish_list_details", indices = arrayOf(Index(value = ["id","original_title"],unique = true)))
data class ResultEntity(
    @PrimaryKey(autoGenerate = true)
    val _id: Long?,
    var adult: Boolean,
    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String,
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "original_language")
    var originalLanguage: String,
    @ColumnInfo(name = "original_title")
    var originalTitle: String,
    @ColumnInfo(name = "overview")
    var overview: String,
    @ColumnInfo(name = "popularity")
    var popularity: Double,
    @ColumnInfo(name = "poster_path")
    var posterPath: String,
    @ColumnInfo(name = "release_date")
    var releaseDate: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "video")
    var video: Boolean,
    @ColumnInfo(name = "vote_average")
    var voteAverage: Double,
    @ColumnInfo(name = "vote_count")
    var voteCount: Int
){
    companion object{
        @JvmStatic
        @BindingAdapter("profileImageEntity")
        fun loadImage(view: ImageView, imageUrl: String) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }

    fun setDate(inputDate: String):String {
        return try{
            val inputDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputDateFormat : DateFormat = SimpleDateFormat("dd MMM yyyy")
            outputDateFormat.format(inputDateFormat.parse(inputDate))
        } catch (e:Exception) {
            inputDate
        }

    }

}