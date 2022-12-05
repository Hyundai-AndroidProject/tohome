package com.example.hyundai_to_home.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "store")
data class StoreEntity (
    @PrimaryKey(autoGenerate = true) var storeId : Int? = null,
    @ColumnInfo(name="storeName") val storeName : String,
    @ColumnInfo(name="storeContent") val storeContent : String
)