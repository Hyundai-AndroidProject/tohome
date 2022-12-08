package com.example.hyundai_to_home.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationDao {

    @Insert
    fun insertReservationDB(reservation:Reservation)

    @Insert
    fun addReservationDB(reservations : List<Reservation>)

    @Query("SELECT s.* from store s, reservation r where r.storeId=s.storeId")
    fun getReservationAll() : List<Store>

    @Query("SELECT s.* from store s, reservation r where r.storeId=s.storeId and r.reservationId =:reservationId")
    fun getReservationOne(reservationId : String): Store?

    @Query("SELECT count(*) from reservation where storeId =:storeId and reservationFixedTime =:reservationFixedTime and reservationSate =:reservationSate and reservationFixedDate =:reservationFixedDate")
    fun countReservation(storeId : Int, reservationFixedTime : String, reservationSate : String, reservationFixedDate : String) : Int

}

// SELECT COUNT(*) from reservation where  storeId =:storeId and reservationFixedTime =:reservationFixedTime