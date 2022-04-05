package com.thorin.dsc.dihealth.ui.navigation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.databinding.FragmentHomeBinding
import com.thorin.dsc.dihealth.ui.navigation.home.detail.NewsActivity
import com.thorin.dsc.dihealth.viewmodel.viewmodelfactory.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        val articleAdapter = ArticleAdapter()

        viewModel.getArticleData().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                articleAdapter.setDataArticle(data)
                articleAdapter.notifyDataSetChanged()
            }
        }

        binding.imageView4.setOnClickListener {
            startActivity(Intent(requireContext(), NewsActivity::class.java))
        }

        binding.imageView2.setOnClickListener {
            Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show()
        }

        binding.imageView6.setOnClickListener {
            Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show()
        }

        viewModel.getQuotes().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                binding.idHomeText.text = data.quotes
                Glide.with(requireActivity())
                    .load(data.photo)
                    .into(binding.imgProfil)
            }
        }

        with(binding.rvHeader) {
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            this.setHasFixedSize(true)
            this.adapter = articleAdapter
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}