package com.example.ms_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.ms_project.MyApplication.Companion.email
//import com.example.ms_project.databinding.ActivityAuthBinding
import com.example.ms_project.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var signUpLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //아마 자동 로그인 기능. 일단 빼놓았음ㅓ
/*        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
        }
        if(MyApplication.checkAuth()){
           //login
           val intent = Intent(this, RunActivity::class.java)  //main page로 이동
           requestLauncher.launch(intent)
       }else {
           //logout
           val intent = Intent(this, MainActivity::class.java)  //login page로 이동
           requestLauncher.launch(intent)
       }*/
        //myCheckPermission(this)
        signUpLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            // 회원가입 액티비티에서 돌아왔을 때의 처리
            if (result.resultCode == RESULT_OK) {
                // 회원가입이 성공하면 처리
                Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                // 회원가입이 취소되거나 실패한 경우
                Toast.makeText(this, "회원가입이 취소되었거나 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.signBtn.setOnClickListener {
            val intent = Intent(this, SignActivity::class.java)
            signUpLauncher.launch(intent)
        }

        binding.loginBtn.setOnClickListener {
            //이메일, 비밀번호 로그인.......................
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "빈칸을 채워주세요", Toast.LENGTH_SHORT).show()
            }else{
                Log.d("team12","email:$email, password:$password")
                MyApplication.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                    binding.inputEmail.text.clear()
                    binding.inputPassword.text.clear()
                    if(task.isSuccessful){
                        if(MyApplication.checkAuth()){
                            MyApplication.email = email
                            val intent = Intent(this, RunActivity::class.java)
                            startActivity(intent)
                        }else {
                            Toast.makeText(baseContext, "전송된 메일로 이메일 인증이 되지 않았습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
