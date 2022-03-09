package com.thorin.dsc.dihealth.ui.navigation.profil

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.databinding.FragmentHomeBinding
import com.thorin.dsc.dihealth.databinding.FragmentProfilBinding
import com.thorin.dsc.dihealth.ui.editprofile.EditProfileActivity


class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.textView2.setOnClickListener {
            Intent(context, EditProfileActivity::class.java).also {
                startActivity(it)
            }
        }

        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}