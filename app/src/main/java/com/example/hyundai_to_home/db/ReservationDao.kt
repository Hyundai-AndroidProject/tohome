package com.example.hyundai_to_home.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationDao {
    @Query("SELECT * FROM reservation")
    fun getReservationAll() : List<ReservationEntity>

    @Insert
    fun insertReservationDB(reservation:ReservationEntity)
}