package com.example.hyundai_to_home.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StoreDao {
    @Query("SELECT * FROM store")
    fun getStoreAll() : List<Store>

    @Query("SELECT * FROM store where storeId = :storeNum ")
    fun getStoreOne(storeNum:Int) : Store

    @Query("SELECT * FROM store WHERE storeDepartment = :departmentName")
    fun getStoreByDepartment(departmentName: String): List<Store>

    @Insert
    fun addStoreDB(stores : List<Store>)
}