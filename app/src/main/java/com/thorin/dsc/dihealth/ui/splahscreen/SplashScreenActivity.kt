package com.thorin.dsc.dihealth.ui.splahscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.databinding.ActivityLoginBinding
import com.thorin.dsc.dihealth.databinding.ActivitySplashScreenBinding
import com.thorin.dsc.dihealth.ui.loginregister.LoginActivity
import com.thorin.dsc.dihealth.ui.mainpage.HomeActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if (null != user) {
                val menuIntent = Intent(this, HomeActivity::class.java)
                startActivity(menuIntent)
                finish()
            } else {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }, 3000)

    }
}