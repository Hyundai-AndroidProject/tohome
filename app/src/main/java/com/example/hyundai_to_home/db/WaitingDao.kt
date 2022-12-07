package com.example.hyundai_to_home.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WaitingDao {
    @Insert
    fun insertWaiting(waiting : Waiting)

    @Query("select * from waiting where memberId = :memberId and storeId = :storeId")
    fun findWaitingById(memberId : String, storeId : Int) : Waiting

    @Insert
    fun addWaitingDB(waiting : List<Waiting>)

    @Query("SELECT s.* from store s, waiting w where w.storeId=s.storeId")
    fun getWaitingAll() : List<Store>

    @Query("SELECT s.* from store s, waiting w where w.storeId=s.storeId and w.waitingId =:waitingId")
    fun getWaitingOne(waitingId : String): Store?
}