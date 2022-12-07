package com.example.hyundai_to_home.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "waiting")
data class Waiting(
    @PrimaryKey(autoGenerate = true) var waitingID: Int? = null,
    @ColumnInfo(name = "memberId") var memberId: String,
    @ColumnInfo(name = "storeId") var storeId: Int,
//    @ColumnInfo(name="waiting_date") var waitingDate : String,
//    @ColumnInfo(name = "waiting_time") var waitingTime :String,

    @ColumnInfo(name = "waiting_date_time") var waitingDateTime: String,
    @ColumnInfo(name = "waiting_headcount") var waitingHeadCount: String,
//    @Embedded var waitingState : WaitingState
    @ColumnInfo(name = "waiting_state") var waitingState: String
)
