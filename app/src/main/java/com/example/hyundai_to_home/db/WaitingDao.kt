package com.example.hyundai_to_home.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WaitingDao {
    @Insert
    fun insertWaiting(waiting : Waiting)

    @Query("select * from waiting where member_id = :memberId and store_id = :storeId")
    fun findWaitingById(memberId : String, storeId : String) : Waiting

}