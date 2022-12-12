package com.example.hyundai_to_home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.example.hyundai_to_home.databinding.ActivityDepartmentListBinding
import com.example.hyundai_to_home.databinding.ActivityWaitingBinding
/**
 * 클래스 설명 : 현대백화점 지점 리스트를 반환하는 클래스
 *
 * @author  정승하
 * 정승하 - 6개의 현대백화점 지점 설정 및 클릭이벤트 설정
 * 정승하 - 클린이벤트로 지점별 식당리스트 반환하도록 구현
 */
class DepartmentListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDepartmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityDepartmentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClose.setOnClickListener {
            finish()
        }

        binding.btn1.setOnClickListener { click(0) }
        binding.btn2.setOnClickListener { click(1) }
        binding.btn3.setOnClickListener { click(2) }
        binding.btn4.setOnClickListener { click(3) }
        binding.btn5.setOnClickListener { click(4) }
        binding.btn6.setOnClickListener { click(5) }
    }

    private fun click(type: Int) {
        val name = when (type) {
            0 -> binding.btn1.text.toString()
            1 -> binding.btn2.text.toString()
            2 -> binding.btn3.text.toString()
            3 -> binding.btn4.text.toString()
            4 -> binding.btn5.text.toString()
            else -> binding.btn6.text.toString()
        }
        val intent = Intent().apply {
            putExtra("name", name)
        }

        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}