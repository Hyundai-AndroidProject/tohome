package com.example.hyundai_to_home.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * 웨이팅 데이터 관련 Entity
 *
 * @author  신기원
 *
 */
@Entity(tableName = "waiting")
data class Waiting(
    @PrimaryKey(autoGenerate = true) var waitingID: Int? = null,
    @ColumnInfo(name = "memberId") var memberId: String,
    @ColumnInfo(name = "memberName") var memberName : String,
    @ColumnInfo(name = "memberPhone") var memberPhone : String,
    @ColumnInfo(name = "storeId") var storeId: Int,
    @ColumnInfo(name = "waiting_date_time") var waitingDateTime: String,
    @ColumnInfo(name = "waiting_headcount") var waitingHeadCount: String,
    @ColumnInfo(name = "waiting_state") var waitingState: String
)
