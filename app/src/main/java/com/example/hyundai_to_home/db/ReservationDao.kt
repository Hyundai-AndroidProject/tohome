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

    @Query("SELECT s.* from store s, reservation r where r.storeId=s.storeId and r.memberId = :memberId")
    fun getReservationStoreAll(memberId: String) : List<Store>

    @Query("SELECT s.* from store s, reservation r where r.storeId=s.storeId and r.reservationId =:reservationId")
    fun getReservationOne(reservationId : String): Store?

    @Query("SELECT count(*) from reservation where storeId =:storeId and reservationFixedTime =:reservationFixedTime and reservationSate =:reservationSate and reservationFixedDate =:reservationFixedDate")
    fun countReservation(storeId : Int, reservationFixedTime : String, reservationSate : String, reservationFixedDate : String) : Int

    // 예약 상세
    @Query("SELECT r.* from reservation r where reservationId =:reservationId")
    fun findReservationById(reservationId : Int) : Reservation

    @Query("UPDATE reservation SET reservationSate =:reservationSate WHERE reservationId =:reservationId")
    fun cancelReservationById(reservationId : Int , reservationSate : String): Int

    @Query("SELECT * from reservation WHERE memberId = :memberId")
    fun getReservation(memberId: String): List<Reservation>

    @Query("SELECT count(*) from reservation ")
    fun getReservationId() : Int

}
