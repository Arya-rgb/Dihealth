package com.thorin.dsc.dihealth.ui.mainpage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.databinding.ActivityHomeBinding
import com.thorin.dsc.dihealth.ui.editprofile.EditProfileViewModel
import com.thorin.dsc.dihealth.ui.navigation.profil.ProfilViewModel
import com.thorin.dsc.dihealth.viewmodel.viewmodelfactory.ViewModelFactory


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[ProfilViewModel::class.java]

        val dataUserSharedPref: SharedPreferences? =
            this.getSharedPreferences("data_user_local", Context.MODE_PRIVATE)

        viewModel.getDataUser().observe(this) { data ->
            if (data.nama != dataUserSharedPref?.getString("name", null)) {
                val edit = dataUserSharedPref?.edit()
                edit?.putString("photo_url", data.photoUrl)
                edit?.putString("name", data.nama)
                edit?.putString("email", data.email)
                edit?.apply()
            }
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_forum, R.id.navigation_chat, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    }



}