package com.nikhil.tmdbtask.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikhil.tmdbtask.data.local.db.entity.ResultEntity

@Dao
interface WishListDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(argResultEntity: ResultEntity):Long

    @Query("select * from wish_list_details")
    suspend fun getWishListDetails():List<ResultEntity>

    @Query("select count(*) from wish_list_details")
    suspend fun getWishListCount():Int

}