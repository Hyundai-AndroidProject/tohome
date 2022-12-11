package com.example.hyundai_to_home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.hyundai_to_home.databinding.ActivityMainBinding
import com.example.hyundai_to_home.databinding.ActivityServiceBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

/**
 * 클래스 설명 : 여러 서비스를 보여주는 클래스, Navigation Drawable을 이용하여 상단의 툴바에서 메뉴창을 확장할 수 있다
 *
 * @author  신기원
 * 신기원 - drawable menu를 위한 툴바와 메뉴 생성
 */
class ServiceActivity : AppCompatActivity(), View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{


    private lateinit var drawerLayout : DrawerLayout
    private lateinit var navigationView : NavigationView
    private  lateinit var  binding : ActivityServiceBinding          // 뷰바인딩 객체
    private var backKeyPressedTime : Long = 0
    private lateinit var MemberId : String      //임시 회원 아이디
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)     //바인딩 객체 연결
        val view = binding.root
        setContentView(view)

        binding.reservation.setOnClickListener(this)        //웨이팅및예약 버튼 클릭 이벤트
        binding.btnLogin.setOnClickListener(this)           //로그인 화면으로 이동

        //액티비티의 앱바(App Bar)로 지정
        setSupportActionBar(binding.toolbar)     //앱바 제어를 위해 툴바 액세스

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기

        getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24); //왼쪽 상단 버튼 아이콘 지정

        drawerLayout = binding.drawerLayout

        navigationView = binding.navigationView
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너


        binding.loginTitle.text ="${MyApplication.email}님 환영합니다."
        binding.btnLogin.text = "다양한 서비스를 이용해보세요"

    }



    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.reservation -> {
                val intent = Intent(this, StoreListActivity::class.java)
                startActivity(intent)
            }


        }
    }
    /**
     * 툴바 메뉴 버튼이 클릭 됐을 때 실행하는 함수
     *
     * @param   툴바 메뉴에서 선택된 item 항목
     * @return  Boolean
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home ->{
                //메뉴 버튼 클릭시 네비게이션 드로어 열기
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 메뉴바가 열렸을 때 item에 대한 클릭 이벤트
     *
     * @param   메뉴에서 선택된 item 항목
     * @return  Boolean
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){

            R.id.mypage -> {
                Log.d("mypage", "dfdf")
                val intent = Intent(this, MypageActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return false
    }

    override fun onBackPressed() {

        if(System.currentTimeMillis() > backKeyPressedTime + 2500){
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this,"뒤로가기 버튼을 한번 더 누르면 종료됩니다",Toast.LENGTH_SHORT).show()
            return;
        }

        if(System.currentTimeMillis() <= backKeyPressedTime +2500){
            finishAffinity()
        }
    }


}