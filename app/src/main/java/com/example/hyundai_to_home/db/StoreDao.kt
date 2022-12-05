package com.example.hyundai_to_home.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StoreDao {
    @Query("SELECT * FROM store")
    fun getStoreAll() : List<StoreEntity>

    @Insert
    fun addStoreDB(stores : List<StoreEntity>)
}