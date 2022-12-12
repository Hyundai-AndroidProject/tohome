package com.example.hyundai_to_home.db


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * 클래스 설명 : 회원 데이터 entity
 *
 * @author  김민규
 * 김민규 - 회원 entity 생성 , memberId를 통해 회원정보 구분, memberName, memberPhone를 통해 아이디 찾기 기능 구현
 */
@Entity(tableName = "member")
data class Member(

    @PrimaryKey() var memberId: String,
    @ColumnInfo(name = "memberName") var memberName : String,
    @ColumnInfo(name = "memberPhone") var memberPhone : String,
    @ColumnInfo(name = "memberBirthdate") var memberBirthdate : String,
    @ColumnInfo(name = "memberAddress") var memberAddress : String,
    @ColumnInfo(name = "memberGender") var memberGender : String
)