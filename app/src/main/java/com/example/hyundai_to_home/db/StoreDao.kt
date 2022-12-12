package com.example.hyundai_to_home.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
/**
 * 클래스 설명 : 식당 데이터 처리를 위한 dao
 *
 * @author  정승하
 * 정승하 - 식당 관련 쿼리문 dao 생성
 */
@Dao
interface StoreDao {
    // 모든 식당 데이터 반환
    @Query("SELECT * FROM store")
    fun getStoreAll() : List<Store>
    
    // 식당 상세 데이터 반환
    @Query("SELECT * FROM store where storeId = :storeNum ")
    fun getStoreOne(storeNum:Int) : Store
    
    // 지점별 식당 리스트 반환
    @Query("SELECT * FROM store WHERE storeDepartment = :departmentName")
    fun getStoreByDepartment(departmentName: String): List<Store>
    
    // 식당 더미데이터 삽입
    @Insert
    fun addStoreDB(stores : List<Store>)
}