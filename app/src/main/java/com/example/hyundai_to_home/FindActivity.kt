package com.example.hyundai_to_home


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivityFindBinding
import com.example.hyundai_to_home.db.AppDatabase
import com.example.hyundai_to_home.db.MemberDao

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityFindBinding
class FindActivity : AppCompatActivity() {

    private lateinit var db : AppDatabase
    private lateinit var memberDao: MemberDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        memberDao = db.MemberDao()




        binding.btnFindID.setOnClickListener{
            if(binding.findIdName.text.toString()==""){
                Toast.makeText(this,"이름을 입력해주세요.",Toast.LENGTH_SHORT).show()
            }
            else{
                if (binding.findIdPhone.text.toString() == ""){
                    Toast.makeText(this,"전화번호를 입력해주세요.",Toast.LENGTH_SHORT).show()
                }
                else{
                    Thread{
                        val findID : String = memberDao.findId(binding.findIdName.text.toString(),binding.findIdPhone.text.toString())
                        binding.result.text = findID

                        if(binding.result.text.toString().equals(findID)){
                            binding.result.text = "고객님의 아이디는 $findID 입니다."
                        }
                        else{
                            binding.result.text = "정보를 정확하게 입력해주세요."
                        }
                    }.start()
                    }
                }
            }

        }
    }
