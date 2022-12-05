package com.example.hyundai_to_home.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName="reservation", primaryKeys = ["member_id","store_id"])
data class ReservationEntity (
    @ColumnInfo(name="memberID") var memberId : Int? = null,
    @ColumnInfo(name="storeID") var storeId : Int? = null,
    @ColumnInfo(name="reservationHeadCount") var reservationHeadCount : Int, // 최대 4명까지
    @ColumnInfo(name="reservationRegisterDate") var reservationRegisterDate: String, // 예약등록날짜 예시 = "20221205"->2022년12월05일
    @ColumnInfo(name="reservationRegisterTime") var reservationRegisterTime: String, // 예약등록시간 예시 = "134530"->13시45분30초
    @ColumnInfo(name="reservationFixedDate") var reservationFixedDate: String, // 예약확정날짜 예시 = "20221205"->2022년12월05일
    @ColumnInfo(name="reservationFixedTime") var reservationFixedTime: String, // 예약확정시간 예시 = "134530"->13시45분30초
    @ColumnInfo(name="reservationSate") var reservationSate : Int // 예약확정 = 1, 예약확정X = 2

)