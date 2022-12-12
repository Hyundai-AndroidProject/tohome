package com.example.hyundai_to_home


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivityFindBinding
import com.example.hyundai_to_home.db.AppDatabase
import com.example.hyundai_to_home.db.MemberDao
/**
 * 클래스 설명 : 회원의 이름과 전화번로를 통해 회원의 아이디를 찾는 클래스
 *
 * @author  김민규
 * 김민규 - 회원의 이름과 전화번호를 입력받고 정확한 값이 입력되었다면 아이디를 보여준다
 * 김민규 - Edittext 에 입력된 값이 없다면 입력을 요청하는 알림창을 띄워준다.
 */
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
            if(binding.findIdName.text.isEmpty()){// 이름을 입력하는 Edittext 에 값을 입력하지 않았을 때 알림창을 띄워준다.
                Toast.makeText(this,"이름을 입력해주세요.",Toast.LENGTH_SHORT).show()
            }
            else{
                if (binding.findIdPhone.text.isEmpty()){// 전화번호를 입력하는 Edittext 에 값을 입력하지 않았을 때 알림창을 띄워준다.
                    Toast.makeText(this,"전화번호를 입력해주세요.",Toast.LENGTH_SHORT).show()
                }
                else{//값이 정확히 입력되었을 때 회원의 아이디를 textview 에 보여준다.
                    Thread{
                        val findID : String = memberDao.findId(binding.findIdName.text.toString(),binding.findIdPhone.text.toString())
                        runOnUiThread {
                            binding.result.text = findID

                            if(binding.result.text.toString().equals(findID)){
                                binding.result.text = "고객님의 아이디는 $findID 입니다."
                            }
                            else{ // 일치하는 아이디가 없을 때 textview 에 보여준다.
                                binding.result.text = "정보를 정확하게 입력해주세요."
                            }
                        }

                    }.start()
                    }
                }
            }

        }
    }
