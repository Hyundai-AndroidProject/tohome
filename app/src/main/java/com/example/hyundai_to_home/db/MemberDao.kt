package com.example.hyundai_to_home.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
/**
 * 클래스 설명 : 회원 데이터 처리를 위한 dao
 *
 * @author  김민규
 * 김민규 - 회원 데이터 처리를 위한 dao 생성
 */
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