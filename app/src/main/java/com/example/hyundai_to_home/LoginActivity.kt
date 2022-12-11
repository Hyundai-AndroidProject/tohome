package com.example.hyundai_to_home


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivityLoginBinding



@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityLoginBinding
private var backKeyPressedTime : Long = 0
class LoginActivity :  AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val terms = binding.terms
        val preview = binding.preview

        terms.setOnClickListener {
            val intent = Intent(this,TermsActivity::class.java)
            startActivity(intent)
        }
        preview.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnFindID.setOnClickListener{
            val intent = Intent(this,FindActivity::class.java )
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {
            //이메일, 비밀번호 로그인.......................
            val email = binding.LoginText.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            if(email.isEmpty()){
                Toast.makeText(this,"아이디를 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else if(password.isEmpty()){
                Toast.makeText(this,"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else{
                MyApplication.auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){ task ->
                        binding.LoginText.text.clear()
                        binding.editTextTextPassword.text.clear()
                        if(task.isSuccessful){
                            if(MyApplication.checkAuth()){
                                print("로그인성공")
                                MyApplication.email = email
                                val intent = Intent(this, ServiceActivity::class.java)
                                startActivity(intent)

                            }else {
                                print("인증안됨")
                                Toast.makeText(baseContext, "전송된 메일로 이메일 인증이 되지 않았습니다.", Toast.LENGTH_SHORT).show()

                            }
                        }else {
                            print("로그인실패")
                            Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

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

