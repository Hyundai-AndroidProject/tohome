package com.example.hyundai_to_home.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(StoreEntity::class, Waiting::class),version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun StoreDao() : StoreDao

    abstract fun waitingDao() : WaitingDao
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
                    //데이터베이스 인스턴스를 생성하고 해당 인스턴스로 DAO인스턴스의 메서드를 사용할 수 있게된다
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
    StoreEntity(1,"올댓커피(Expresso Caffe/B1F)","브라질 & 엘살바도르 C.O.E", "https://user-images.githubusercontent.com/46221990/205533590-85e06f3b-124c-4150-8593-428783f56241.jpg", "더현대서울"),
    StoreEntity(2,"에그슬럿(B1F)","동물복지달걀로 만든 LA 오리지널 에그 샌드위치", "https://user-images.githubusercontent.com/46221990/205533525-38bca011-3c55-4b43-9e13-90b3a5f205e1.jpg", "더현대서울"),
    StoreEntity(3,"나의가야(6F)","35년 전통을 가진 나의 가야는 불고기 및 각종 전골 요리가 유명합니다.", "https://user-images.githubusercontent.com/52660729/205539898-1134787c-046b-46a4-b99f-dec89ee72bc3.jpg", "더현대서울"),
    StoreEntity(4,"가족회관(6F)","국내 유일 전주비빔밥 기능 보유자 김년임 장인의 손맛을 맛볼 수 있습니다.", "https://user-images.githubusercontent.com/52660729/205539936-06521d27-ee82-49cb-a869-1bf05c829cb6.jpg", "더현대서울"),
    StoreEntity(5,"송(6F)","30년간 한결같은 맛으로 자리를 지켜온 일본식 우동&메밀 전문점입니다.","https://user-images.githubusercontent.com/52660729/205539946-8f1e67f6-b457-4a18-a301-3803a9344525.jpg","더현대서울"),

    //StoreEntity(6,"송(6F)","30년간 한결같은 맛으로 자리를 지켜온 일본식 우동&메밀 전문점입니다.","https://user-images.githubusercontent.com/52660729/205539946-8f1e67f6-b457-4a18-a301-3803a9344525.jpg","목동"),
    StoreEntity(7,"사위식당(6F)","장모님이 해주셨던 낙곱새의 맛을 잊지 못한 두 사위가 오랜시간 연구하여 준비한 식당입니다.", "https://user-images.githubusercontent.com/52660729/205539955-53357bcb-ea29-4735-9b11-5e436dfa568f.jpg", "목동"),
    StoreEntity(8,"와인웍스(6F)","와인 중심의 복합문화공간, 와인웍스 프리미엄 와인&다이닝라이프를 만나보세요","https://user-images.githubusercontent.com/52660729/205539959-71b489f6-5290-4fb6-942b-5dd044b047ad.jpg","목동")
)

