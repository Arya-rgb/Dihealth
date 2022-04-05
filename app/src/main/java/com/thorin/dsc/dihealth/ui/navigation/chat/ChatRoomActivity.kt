package com.thorin.dsc.dihealth.ui.navigation.chat

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.thorin.dsc.dihealth.data.source.remote.response.ListUserResponse
import com.thorin.dsc.dihealth.databinding.ActivityChatRoomBinding
import com.thorin.dsc.dihealth.viewmodel.viewmodelfactory.ViewModelFactory

class ChatRoomActivity : AppCompatActivity() {

    private var _binding: ActivityChatRoomBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        val detailChat =
            intent.getParcelableExtra<ListUserResponse>(ChatRoomActivity.EXTRA_CHAT) as ListUserResponse

        val factory = ViewModelFactory.getInstance(this)
        val viewModel =
            ViewModelProvider(this, factory)[ChatViewModel::class.java]

        val dataUserSharedPref: SharedPreferences? =
            this.getSharedPreferences("data_user_local", Context.MODE_PRIVATE)

        var email1 = detailChat.email_user
        var email2 = mAuth.currentUser?.email

        email1 = email1.replace(".", "")
        email2 = email2?.replace(".", "")

        val chatId = email1

        val messageAdapter = ListMessageAdapter()

        viewModel.getListMessage(chatId).observe(this) {
            if (it != null) {
                messageAdapter.setDataMessage(it)
                messageAdapter.notifyDataSetChanged()
            }
        }

        with(binding.recyclerView) {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = messageAdapter
        }

        Toast.makeText(this, "Please Be Polite", Toast.LENGTH_SHORT).show()
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        binding.btSend.setOnClickListener {
            viewModel.uploadChat(
                binding.etMessage.text.toString(),
                dataUserSharedPref?.getString("photo_url", null).toString(),
                chatId,
                dataUserSharedPref?.getString("name", null).toString(),
                mAuth.currentUser?.uid.toString()
            )
        }
    }

    companion object {
        const val EXTRA_CHAT = "extra_chat"
    }

}