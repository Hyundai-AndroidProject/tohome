package com.example.hyundai_to_home.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
/**
 * 클래스 설명 : 예약 데이터 처리를 위한 dao
 *
 * @author  박서은
 * 박서은 - 예약을 위한 dao 생성
 */
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

    // 가게별로 날짜별 시간에 예약한 인원수를 반환 -> 예약인원 제한을 위해
    @Query("SELECT count(*) from reservation where storeId =:storeId and reservationFixedTime =:reservationFixedTime and reservationSate =:reservationSate and reservationFixedDate =:reservationFixedDate")
    fun countReservation(storeId : Int, reservationFixedTime : String, reservationSate : String, reservationFixedDate : String) : Int

    // 예약 상세
    @Query("SELECT r.* from reservation r where reservationId =:reservationId")
    fun findReservationById(reservationId : Int) : Reservation

    // 예약 상태 변경
    @Query("UPDATE reservation SET reservationSate =:reservationSate WHERE reservationId =:reservationId")
    fun cancelReservationById(reservationId : Int , reservationSate : String): Int

    // 사용자별 예약 리스트
    @Query("SELECT * from reservation WHERE memberId = :memberId")
    fun getReservation(memberId: String): List<Reservation>

    // reservationid 가져오기
    @Query("SELECT count(*) from reservation ")
    fun getReservationId() : Int

}
