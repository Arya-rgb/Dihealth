package com.thorin.dsc.dihealth.ui.navigation.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thorin.dsc.dihealth.data.source.remote.response.UserPostResponse
import com.thorin.dsc.dihealth.databinding.FragmentForumBinding
import com.thorin.dsc.dihealth.ui.editprofile.EditProfileViewModel
import com.thorin.dsc.dihealth.viewmodel.viewmodelfactory.ViewModelFactory


class ForumFragment : Fragment() {

    private var _binding: FragmentForumBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForumBinding.inflate(inflater, container, false)

        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel = ViewModelProvider(this, factory)[ForumViewModel::class.java]

        val postForumAdapter = ForumListAdapter()

        binding.idEdit.setOnClickListener {
            AddPostItemDialog(requireContext(), object : AddPostListener {
                override fun onAddButtonClicked(item: UserPostResponse) {
                    viewModel.getDataUploadPost(
                        item.photoUrl.toString(),
                        item.uid.toString(),
                        item.nama.toString(),
                        item.email.toString(),
                        item.post.toString()
                    )
                }
            }).show()
        }

        viewModel.getPostData().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                postForumAdapter.setDataPost(data)
                postForumAdapter.notifyDataSetChanged()
            }
        }

        with(binding.postRv) {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = postForumAdapter
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}