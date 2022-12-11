package com.example.hyundai_to_home.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemberDao {

    @Insert
    fun insertMember(member: Member)

    @Query("SELECT * FROM member WHERE memberId = :memberId")
    fun getMemberInfo(memberId : String) : Member

    @Query("DELETE FROM member WHERE memberId = :memberId")
    fun deleteMember(memberId:String)

    @Query("SELECT memberId FROM member WHERE memberName = :memberName AND memberPhone = :memberPhone")
    fun findId(memberName : String, memberPhone : String) : String

    @Query("SELECT count(*) FROM member WHERE memberId = :memberId")
    fun duplicateID(memberId: String?) : Int



}