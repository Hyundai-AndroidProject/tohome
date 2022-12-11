package com.example.hyundai_to_home.db


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "member")
data class Member(

    @PrimaryKey() var memberId: String,
    @ColumnInfo(name = "memberName") var memberName : String,
    @ColumnInfo(name = "memberPhone") var memberPhone : String,
    @ColumnInfo(name = "memberBirthdate") var memberBirthdate : String,
    @ColumnInfo(name = "memberAddress") var memberAddress : String,
    @ColumnInfo(name = "memberGender") var memberGender : String
)