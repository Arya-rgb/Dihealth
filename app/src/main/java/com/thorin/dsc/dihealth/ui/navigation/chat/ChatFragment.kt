package com.thorin.dsc.dihealth.ui.navigation.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.thorin.dsc.dihealth.databinding.FragmentChatBinding
import com.thorin.dsc.dihealth.viewmodel.viewmodelfactory.ViewModelFactory

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)


        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel =
            ViewModelProvider(this, factory)[ChatViewModel::class.java]

        //coming soon

        binding.floatingActionButton.setOnClickListener {
            Intent(requireContext(), ListUserActivity::class.java).also {
                startActivity(it)
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}