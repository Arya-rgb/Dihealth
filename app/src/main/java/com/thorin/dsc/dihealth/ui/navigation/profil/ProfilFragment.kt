package com.thorin.dsc.dihealth.ui.navigation.profil

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.databinding.FragmentProfilBinding
import com.thorin.dsc.dihealth.ui.editprofile.EditProfileActivity
import com.thorin.dsc.dihealth.viewmodel.viewmodelfactory.ViewModelFactory


class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.textView2.setOnClickListener {
            Intent(context, EditProfileActivity::class.java).also {
                startActivity(it)
            }
        }
        val dataUserSharedPref: SharedPreferences? =
            context?.getSharedPreferences("data_user_local", Context.MODE_PRIVATE)

        binding.textView2.text = dataUserSharedPref?.getString("name", null)
        binding.textView3.text = dataUserSharedPref?.getString("email", null)
        Glide.with(requireActivity())
            .load(dataUserSharedPref?.getString("photo_url", null))
            .placeholder(R.drawable.google_logo)
            .into(binding.imageView5)
        

        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}