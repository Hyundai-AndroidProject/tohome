package com.example.hyundai_to_home.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * 클래스 설명 : 식당 데이터 entity
 *
 * @author  정승하
 * 정승하 - 식당 entity 생성 , storeid를 통해 식당 구분
 */
@Entity(tableName = "store")
data class Store (
    @PrimaryKey(autoGenerate = true) var storeId : Int? = null,
    @ColumnInfo(name="storeName") val storeName : String,
    @ColumnInfo(name="storeContent") val storeContent : String,
    @ColumnInfo(name="storeImage") val storeImage : String,
    @ColumnInfo(name="storeDepartment") val storeDepartment : String
)