package com.developeralamin.ecity.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.developeralamin.ecity.R
import com.developeralamin.ecity.databinding.ActivitySignupBinding
import com.developeralamin.ecity.model.UserModel
import com.developeralamin.ecity.utlis.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding
    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.loginBtn.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

        binding.regBtn.setOnClickListener {
            val name = binding.inputName.text.toString()
            val phone = binding.inputPhone.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            if (name.isEmpty()) {
                binding.inputName.setError("নাম")
                binding.inputName.requestFocus()
            } else if (phone.isEmpty()) {
                binding.inputPhone.setError("ফোন")
                binding.inputPhone.requestFocus()
            } else if (email.isEmpty()) {
                binding.inputEmail.setError("ইমেইল")
                binding.inputEmail.requestFocus()
            } else if (password.isEmpty()) {
                binding.inputPassword.setError("পাসওয়ার্ড")
                binding.inputPassword.requestFocus()
            } else {
                Config.showDialog(this)
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    Config.hideDialog()
                    if (it.isSuccessful) {
                        val curretUserId = auth.currentUser!!.uid
                        val data = UserModel(
                            curretUserId,
                            name,
                            phone,
                            email,
                            password
                        )

                        db.collection("user").document(curretUserId).set(data)
                            .addOnCompleteListener {
                                startActivity(Intent(applicationContext, MainActivity::class.java))
                                finish()
                                Toast.makeText(
                                    applicationContext,
                                    "রেজিস্ট্রেশন সম্পন্ন হয়েছে",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                    } else {
                        Toast.makeText(
                            applicationContext,
                            it.exception!!.localizedMessage.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}