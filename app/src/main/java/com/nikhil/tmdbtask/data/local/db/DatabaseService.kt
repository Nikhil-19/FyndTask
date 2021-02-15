package com.nikhil.motialoswaltask.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikhil.tmdbtask.data.local.db.dao.WishListDAO
import com.nikhil.tmdbtask.data.local.db.entity.ResultEntity

@Database(entities = [ResultEntity::class],version = 1,exportSchema = false)
abstract class DatabaseService:RoomDatabase() {

   companion object{
      private lateinit var databaseService: DatabaseService
       fun getDatabaseService(argContext: Context):DatabaseService{
          databaseService= Room.databaseBuilder(
               argContext,
               DatabaseService::class.java,
               "wish_list_db"
           ).build()
           return databaseService
       }
   }
   abstract fun getDAO(): WishListDAO



}