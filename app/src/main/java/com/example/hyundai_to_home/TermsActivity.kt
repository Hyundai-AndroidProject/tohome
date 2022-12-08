package com.example.hyundai_to_home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivityTermsBinding


@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityTermsBinding


class TermsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val goSignup = binding.goSignup
        val goBackLogin = binding.goBackLogin
        val preview = binding.Preview


        binding.checkAll.setOnClickListener { onCheckChangedTerms(binding.checkAll) }
        binding.checkTerm1.setOnClickListener { onCheckChangedTerms(binding.checkTerm1) }
        binding.checkTerm2.setOnClickListener { onCheckChangedTerms(binding.checkTerm2) }
        binding.checkTerm3.setOnClickListener { onCheckChangedTerms(binding.checkTerm3) }
        binding.checkTerm4.setOnClickListener { onCheckChangedTerms(binding.checkTerm4) }

        binding.marketingAll.setOnClickListener{ onCheckChangedMarketing(binding.marketingAll)}
        binding.marketingPush.setOnClickListener{ onCheckChangedMarketing(binding.marketingPush)}
        binding.marketingSMS.setOnClickListener{onCheckChangedMarketing(binding.marketingSMS)}
        binding.marketingEmail.setOnClickListener{onCheckChangedMarketing(binding.marketingEmail)}



        goSignup.setOnClickListener {
            if(binding.checkTerm1.isChecked){
                if(binding.checkTerm2.isChecked){
                    if(binding.untilSecede.isChecked) {
                        val intent = Intent(this, SignupActivity::class.java)
                        startActivity(intent)
                    }
                    else if(binding.year.isChecked){
                        val intent = Intent(this, SignupActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,"개인정보 유효기간 동의를 체크해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this,"개인정보 수집·이용 동의를 체크해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,"이용약관을 체크해주세요.", Toast.LENGTH_SHORT).show()
            }

        }

        goBackLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        preview.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onCheckChangedTerms(checkBox: CheckBox) {
        when (checkBox.id) {
            R.id.checkAll -> {
                if (binding.checkAll.isChecked) {
                    binding.checkTerm1.isChecked = true
                    binding.checkTerm2.isChecked = true
                    binding.checkTerm3.isChecked = true
                    binding.checkTerm4.isChecked = true
                } else {
                    binding.checkTerm1.isChecked = false
                    binding.checkTerm2.isChecked = false
                    binding.checkTerm3.isChecked = false
                    binding.checkTerm4.isChecked = false
                }
            }
            else -> {
                binding.checkAll.isChecked = (
                        binding.checkTerm1.isChecked
                                && binding.checkTerm2.isChecked
                                && binding.checkTerm3.isChecked
                                && binding.checkTerm4.isChecked)

            }


        }
    }

    private fun onCheckChangedMarketing(checkBox: CheckBox){
        when(checkBox.id){
            R.id.marketingAll ->{
                if(binding.marketingAll.isChecked){
                    binding.marketingPush.isChecked = true
                    binding.marketingSMS.isChecked = true
                    binding.marketingEmail.isChecked = true
                }
                else {
                    binding.marketingPush.isChecked = false
                    binding.marketingSMS.isChecked = false
                    binding.marketingEmail.isChecked = false
                }
            }
            else -> {
                binding.marketingAll.isChecked = (
                        binding.marketingPush.isChecked
                                && binding.marketingSMS.isChecked
                                && binding.marketingEmail.isChecked
                        )

            }
        }
    }

}