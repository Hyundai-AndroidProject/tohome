package com.example.hyundai_to_home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.hyundai_to_home.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

//, NavigationView.OnNavigationItemSelectedListener
class MainActivity : AppCompatActivity(), View.OnClickListener{


    private  lateinit var  binding : ActivityMainBinding          // 뷰바인딩 객체

    private lateinit var MemberId : String      //임시 회원 아이디
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)     //바인딩 객체 연결
        val view = binding.root
        setContentView(view)

        binding.btnMain.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btn_main -> {
                val intent = Intent(this, ServiceActivity::class.java)
                startActivity(intent)
            }
        }
    }

}