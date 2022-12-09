package com.example.hyundai_to_home.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
/**
 * Waiting 데이터 접근 객체
 *
 * @author  신기원
 * 신기원 - findWaitingById, insertWaiting, waitingCancel, waitingSameStore 생성
 */
@Dao
interface WaitingDao {
    @Insert
    fun insertWaiting(waiting : Waiting)

    @Update
    fun waitingCancel(waiting: Waiting)


    @Query("select * from waiting where memberId = :memberId and storeId = :storeId")
    fun findWaitingById(memberId : String, storeId : Int) : Waiting

    @Insert
    fun addWaitingDB(waiting : List<Waiting>)

    @Query("SELECT s.* from store s, waiting w where w.storeId=s.storeId and w.memberId = :memberId")
    fun getWaitingAll(memberId: String) : List<Store>

    @Query("SELECT w.* from store s, waiting w where w.storeId=s.storeId and w.memberId = :memberId")
    fun getWaiting(memberId: String) : List<Waiting>

    @Query("SELECT w.* from store s, waiting w where w.storeId=s.storeId and w.storeId =:storeId")
    fun getWaitingOne(storeId : Int): Waiting?

    @Query("select count(*) from waiting where storeId = :storeId")
    fun waitingSameStore(storeId: Int) : Int

}