package com.thorin.dsc.dihealth.ui.editprofile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.databinding.ActivityEditProfileBinding
import com.thorin.dsc.dihealth.ui.mainpage.HomeActivity
import com.thorin.dsc.dihealth.viewmodel.viewmodelfactory.ViewModelFactory

class EditProfileActivity : AppCompatActivity() {

    private var _binding: ActivityEditProfileBinding? = null
    private val binding get() = _binding!!

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[EditProfileViewModel::class.java]

        binding.btnEdit.setOnClickListener {

            if (binding.idName.text.isNullOrEmpty()) {
                showNameAlert()
                Snackbar.make(binding.root, "Name Must Not Be Empty !", Snackbar.LENGTH_SHORT)
                    .show()

            } else {

                viewModel.uploadDataUser(
                    mAuth.currentUser?.photoUrl.toString(),
                    mAuth.currentUser?.uid.toString(),
                    binding.idName.text.toString(),
                    mAuth.currentUser?.email.toString()
                ).observe(this) { data ->

                    val dataUserSharedPref: SharedPreferences =
                        this.getSharedPreferences("data_user_local", Context.MODE_PRIVATE)
                    val edit = dataUserSharedPref.edit()
                    edit?.putString("name", data.nama)
                    edit?.putString("email", data.email)
                    edit?.putString("photo_url", data.photoUrl)
                    edit?.apply()

                }

                Intent(this, HomeActivity::class.java).also {
                    startActivity(it)
                    Toast.makeText(this, "Data Will Be Updated, Make Sure You Have Internet Connection", Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    private fun showNameAlert() {
        binding.idName.error = getString(R.string.name_empty)
    }
}