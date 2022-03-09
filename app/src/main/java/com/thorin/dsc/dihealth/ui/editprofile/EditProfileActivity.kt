package com.thorin.dsc.dihealth.ui.editprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.databinding.ActivityEditProfileBinding
import com.thorin.dsc.dihealth.databinding.ActivityLoginBinding
import com.thorin.dsc.dihealth.viewmodel.viewmodelfactory.ViewModelFactory

class EditProfileActivity : AppCompatActivity() {

    private var _binding: ActivityEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[EditProfileViewModel::class.java]
        val nama = binding.idName.text.toString()

        viewModel.uploadDataUser("kjhkjhasd", nama, "dicky@mail.com").observe(this) {data ->
            data.uid = "kjhkjhasd"
            data.nama = binding.idName.text.toString()
            data.email = "dicky@mail.com"
        }



    }
}