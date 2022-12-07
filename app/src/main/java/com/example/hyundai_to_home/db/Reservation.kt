package com.example.hyundai_to_home.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="reservation")
data class Reservation(
    @PrimaryKey(autoGenerate = true) var reservationID: Int? = null,
    @ColumnInfo(name="memberId") var memberId : String,
    @ColumnInfo(name="storeId") var storeId : Int,
    @ColumnInfo(name="reservationHeadCount") var reservationHeadCount : String, // 최대 4명까지
    @ColumnInfo(name="reservationContent") var reservationContent : String,     // 요청사항
    @ColumnInfo(name="reservationRegisterDate") var reservationRegisterDate: String, // 예약등록날짜 예시 = "20221205"->2022년12월05일
    //@ColumnInfo(name="reservationRegisterTime") var reservationRegisterTime: String, // 예약등록시간 예시 = "134530"->13시45분30초
    @ColumnInfo(name="reservationFixedDate") var reservationFixedDate: String, // 예약확정날짜 예시 = "20221205"->2022년12월05일
    @ColumnInfo(name="reservationFixedTime") var reservationFixedTime: String, // 예약확정시간 예시 = "134530"->13시45분30초
    @ColumnInfo(name="reservationSate") var reservationSate : String // 예약확정 = 1, 예약확정X = 2

)