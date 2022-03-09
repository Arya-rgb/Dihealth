package com.thorin.dsc.dihealth.ui.loginregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.databinding.ActivityLoginBinding
import com.thorin.dsc.dihealth.databinding.ActivityLoginEmailBinding

class LoginEmailActivity : AppCompatActivity() {

    private var _binding: ActivityLoginEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Login"
    }
}