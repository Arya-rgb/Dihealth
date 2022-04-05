package com.thorin.dsc.dihealth.ui.navigation.profil

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.databinding.FragmentProfilBinding
import com.thorin.dsc.dihealth.ui.editprofile.EditProfileActivity
import com.thorin.dsc.dihealth.ui.loginregister.LoginActivity


class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

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


        binding.button3.setOnClickListener {
            userChoiceLogout()
        }

        return root

    }

    private fun userChoiceLogout() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Are You Sure ?")
        builder.setMessage(
            """
            You Will Be Logged out
        """.trimIndent()
        )
        builder.setPositiveButton("Yes") { _, _ ->

            val sharedPrefUserData: SharedPreferences? =
                activity?.getSharedPreferences("data_user_local", Context.MODE_PRIVATE)

            sharedPrefUserData?.edit()?.clear()?.apply()

            mAuth.signOut()
            Intent(activity, LoginActivity::class.java).also { back ->
                startActivity(back)
                activity?.finish()
            }
        }
        builder.setNegativeButton("No") { dialog, _ -> // Do nothing
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}