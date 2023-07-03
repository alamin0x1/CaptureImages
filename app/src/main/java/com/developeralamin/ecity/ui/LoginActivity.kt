package com.developeralamin.ecity.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.developeralamin.ecity.databinding.ActivityLoginBinding
import com.developeralamin.ecity.utlis.Config
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()

        binding.regBtn.setOnClickListener {
            startActivity(Intent(applicationContext, SignupActivity::class.java))
        }

        binding.loginBtn.setOnClickListener {

            val email = binding.inputEmail.text.toString()
            val password = binding.inputEmail.text.toString()

            if (email.isEmpty()){
                binding.inputEmail.setError("ইমেইল")
                binding.inputEmail.requestFocus()
            }else if (password.isEmpty()){
                binding.inputPassword.setError("পাসওয়ার্ড")
                binding.inputPassword.requestFocus()
            }else{
                Config.showDialog(this)

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    Config.hideDialog()
                    if (it.isSuccessful) {
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                        Toast.makeText(applicationContext, "লগইন সম্পন্ন হয়েছে", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(applicationContext, it.exception!!.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                    }

                    Config.hideDialog()
                }
            }
        }
    }
}