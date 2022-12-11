package com.example.hyundai_to_home.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Store::class, Waiting::class, Reservation::class,Member::class],version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun StoreDao() : StoreDao
    abstract fun waitingDao() : WaitingDao
    abstract fun ReservationDao() : ReservationDao
    abstract fun MemberDao() : MemberDao
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
                            fillStoreInDb(context.applicationContext)
                            //fillWaitingInDb(context.applicationContext)
                            //fillReservationInDb(context.applicationContext)
                        }
                    }).build()
                }
            }
            return  appDatabase
        }
        fun fillStoreInDb(context: Context){
            CoroutineScope(Dispatchers.IO).launch {
                getInstance(context)!!.StoreDao().addStoreDB(
                    STORE_DATA
                )
            }
        }
    }
}
private val STORE_DATA = arrayListOf(
    //더현대서울
    Store(1,"올댓커피(Expresso Caffe/B1F)","브라질 & 엘살바도르 C.O.E", "https://user-images.githubusercontent.com/46221990/205533590-85e06f3b-124c-4150-8593-428783f56241.jpg", "더현대서울"),
    Store(2,"에그슬럿(B1F)","동물복지달걀로 만든 LA 오리지널 에그 샌드위치", "https://user-images.githubusercontent.com/46221990/205533525-38bca011-3c55-4b43-9e13-90b3a5f205e1.jpg", "더현대서울"),
    Store(3,"나의가야(6F)","35년 전통을 가진 나의 가야는 불고기 및 각종 전골 요리가 유명합니다.", "https://user-images.githubusercontent.com/52660729/205539898-1134787c-046b-46a4-b99f-dec89ee72bc3.jpg", "더현대서울"),
    Store(4,"가족회관(6F)","국내 유일 전주비빔밥 기능 보유자 김년임 장인의 손맛을 맛볼 수 있습니다.", "https://user-images.githubusercontent.com/52660729/205539936-06521d27-ee82-49cb-a869-1bf05c829cb6.jpg", "더현대서울"),
    Store(5,"송(6F)","30년간 한결같은 맛으로 자리를 지켜온 일본식 우동&메밀 전문점입니다.","https://user-images.githubusercontent.com/52660729/205539946-8f1e67f6-b457-4a18-a301-3803a9344525.jpg","더현대서울"),
    //목동점
    Store(6,"송(6F)","30년간 한결같은 맛으로 자리를 지켜온 일본식 우동&메밀 전문점입니다.","https://user-images.githubusercontent.com/52660729/205539946-8f1e67f6-b457-4a18-a301-3803a9344525.jpg","목동점"),
    Store(7,"사위식당(6F)","장모님이 해주셨던 낙곱새의 맛을 잊지 못한 두 사위가 오랜시간 연구하여 준비한 식당입니다.", "https://user-images.githubusercontent.com/52660729/205539955-53357bcb-ea29-4735-9b11-5e436dfa568f.jpg", "목동점"),
    Store(8,"와인웍스(6F)","와인 중심의 복합문화공간, 와인웍스 프리미엄 와인&다이닝라이프를 만나보세요","https://user-images.githubusercontent.com/52660729/205539959-71b489f6-5290-4fb6-942b-5dd044b047ad.jpg","목동점"),
    Store(9,"순옥이네 명가(6F)", "재료와 신선도와 양, 맛까지 만족시켜주는 제주도 가정식 반상을 선보입니다.", "https://user-images.githubusercontent.com/52660729/206346599-abf3f759-544d-4d00-bd10-d0abe68d8a94.jpg","목동점"),
    Store(10, "랑만(6F)","현지에서 공수한 다양한 식재료를 사용하는 베트남 음식점입니다.","https://user-images.githubusercontent.com/52660729/206346622-3eb1d204-dd41-4519-90df-c88ae1804bfd.jpg","목동점"),
    //천호점
    Store(11,"SMT 라운지(6F)", "SM 엔터테인먼트가 운영하는 프라이빗 레스토랑 브랜드 'SMT HOUSE'의 새로운 브랜드입니다.", "https://user-images.githubusercontent.com/52660729/206346615-9e346a5f-c9ce-4273-be25-0858c98e1cbe.jpg", "천호점"),
    Store(12,"이탈리 레스토랑(6F)", "현지에서 공수한 양질의 식재료를 사용한 음식으로 이탈리안 식문화를 제대로 즐길 수 있습니다.","https://user-images.githubusercontent.com/52660729/206346613-f0794521-1b6f-4ded-a83a-b18bfb043d40.jpg","천호점"),
    Store(13, "도원스타일(6F)", "정통 중식을 현대적인 공간에서 만날 수 있습니다.", "https://user-images.githubusercontent.com/52660729/206346609-f7ed7384-bb15-47be-93a0-a774edcb9a30.jpg","천호점"),
    Store(14, "로라스블랑(6F)", "백색 월계수라는 의미를 가진 로라스 블랑은 음식은 물론 접시 하나에도 유럽의 느끼을 담아낸 뉴 클래식 브런치 카페입니다.","https://user-images.githubusercontent.com/52660729/206346606-babbbe88-0258-45f6-844b-17844cb79232.jpg","천호점"),
    Store(15, "오븐(2F)","차, 커피와 함께 먹을 수 있는 다양한 나라의 구운과자를 새로운 시작으로 재해석해 익숙하지만 새로운 맛을 만드는 카페","https://user-images.githubusercontent.com/52660729/206346603-9f7cf697-1a2d-4ffa-8fe1-852647bbb9f2.jpg","천호점")
    //신촌점
    //Store(16),
    //Store(17),
    //Store(18),
    //Store(19),
    //Store(20),

    //디큐브시티
    //Store(21),
    //Store(22),
    //Store(23),
    //Store(24),
    //Store(25),

    //판교점
    //Store(26),
    //Store(27),
    //Store(28),
    //Store(29),
    //Store(30),
)

private val WAITING_DATA = arrayListOf(
    Waiting(1,"tmdgk95","승하","010-1234-1234",1,"2022-12-07 13:24:30", "4", "예약완료"),
    Waiting(2,"tmdgk95","승하","010-1234-1234",3,"2022-12-07 14:25:35", "3", "예약완료")
)

private val RESERVATION_DATA = arrayListOf(
    Reservation(1,"tmdgk95", 1, "4","예약 컨텐츠", "2022-12-07","2022-12-25","10:30", "예약완료"),
    Reservation(2,"tmdgk95", 2, "2","예약 컨텐츠2", "2022-12-07","2022-12-25","10:30", "예약완료"),
    Reservation(3,"rldnjs98", 5, "3","예약 컨텐츠3", "2022-12-07","2022-12-25","10:30", "예약완료")
)