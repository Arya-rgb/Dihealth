package com.thorin.dsc.dihealth.ui.navigation.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thorin.dsc.dihealth.databinding.ActivityListUserBinding
import com.thorin.dsc.dihealth.ui.editprofile.EditProfileViewModel
import com.thorin.dsc.dihealth.viewmodel.viewmodelfactory.ViewModelFactory

class ListUserActivity : AppCompatActivity() {

    private var _binding: ActivityListUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listUserAdapter = ListUserAdapter()

        val factory = ViewModelFactory.getInstance(this)
        val viewModel =
            ViewModelProvider(this, factory)[ChatViewModel::class.java]

        viewModel.getListUser().observe(this) { data ->
            if (data != null) {
                listUserAdapter.setDataUser(data)
                listUserAdapter.notifyDataSetChanged()
            }
        }

        with(binding.rvUser) {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = listUserAdapter
        }

    }

}