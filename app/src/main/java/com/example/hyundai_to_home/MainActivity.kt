package com.example.hyundai_to_home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.hyundai_to_home.databinding.DrawlayoutBinding
import com.google.android.material.navigation.NavigationView

//, NavigationView.OnNavigationItemSelectedListener
class MainActivity : AppCompatActivity(), View.OnClickListener{


    private lateinit var drawerLayout : DrawerLayout
    private lateinit var navigationView : NavigationView
    private  lateinit var  binding : DrawlayoutBinding          // 뷰바인딩 객체
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DrawlayoutBinding.inflate(layoutInflater)     //바인딩 객체 연결
        val view = binding.root
        setContentView(view)

        binding.reservation.setOnClickListener(this)        //웨이팅및예약 버튼 클릭 이벤트
        binding.btnLogin.setOnClickListener(this)           //로그인 화면으로 이동

        //액티비티의 앱바(App Bar)로 지정
        setSupportActionBar(binding.toolbar)     //앱바 제어를 위해 툴바 액세스

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기

        getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24); //왼쪽 상단 버튼 아이콘 지정

        drawerLayout = binding.drawerLayout


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home ->{
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.reservation -> {
                val intent = Intent(this, StoreListActivity::class.java)
                startActivity(intent)
            }

//            R.id.btn_login -> {
//                val intent = Intent(this, MainActivity2::class.java)
//                startActivity(intent)
//            }
        }
    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when (item.itemId){
//        }
//    }


}