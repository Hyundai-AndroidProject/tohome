package com.example.hyundai_to_home.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [StoreEntity::class],version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun StoreDao() : StoreDao

    companion object {
        @Volatile
        var appDatabase : AppDatabase? = null

        fun getInstance(context : Context) : AppDatabase? {
            /*if(appDatabase == null){
                appDatabase =  Room.databaseBuilder(context,
                    AppDatabase::class.java,
                    databaseName).
                fallbackToDestructiveMigration()
                    .build()
            }*/
            if(appDatabase == null){
                synchronized(AppDatabase::class){
                    appDatabase = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "database"
                    ).addCallback(object : RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            fillInDb(context.applicationContext)
                        }
                    }).build()
                }
            }
            return  appDatabase
        }
        fun fillInDb(context: Context){
            CoroutineScope(Dispatchers.IO).launch {
                getInstance(context)!!.StoreDao().addStoreDB(
                    STORE_DATA
                )
            }
        }
    }


}
private val STORE_DATA = arrayListOf(
    StoreEntity(1,"올댓커피(Expresso Caffe/B1F)","브라질 & 엘살바도르 C.O.E"),
    StoreEntity(2,"에그슬럿(B1F)","동물복지달걀로 만든 LA 오리지널 에그 샌드위치"),
    StoreEntity(3,"나의가야(6F)","35년 전통을 가진 나의 가야는 불고기 및 각종 전골 요리가 유명합니다."),
    StoreEntity(4,"가족회관(6F)","국내 유일 전주비빔밥 기능 보유자 김년임 장인의 손맛을 맛볼 수 있습니다.")
)