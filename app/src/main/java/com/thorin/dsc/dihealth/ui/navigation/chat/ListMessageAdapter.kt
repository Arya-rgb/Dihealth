package com.thorin.dsc.dihealth.ui.navigation.chat

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.data.source.remote.response.MessageListResponse
import com.thorin.dsc.dihealth.databinding.ChatItemBinding

class ListMessageAdapter : RecyclerView.Adapter<ListMessageAdapter.ViewHolder>() {

    private var listMessage = ArrayList<MessageListResponse>()

    fun setDataMessage(dataMessage: List<MessageListResponse>?) {

        if (dataMessage != null) {
            this.listMessage.clear()
            this.listMessage.addAll(dataMessage)
        }

    }

    class ViewHolder(private val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dataMessage: MessageListResponse) {

            val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

            with(binding) {
                txtmessage.text = dataMessage.message

                if (dataMessage.uid.toString() == mAuth.currentUser?.uid) {
                    itemChat.setBackgroundResource(R.drawable.shape_bg_incoming_bubble)
                } else {
                    itemChat.setBackgroundResource(R.drawable.shape_bg_outgoing_bubble)
                    txtmessage.gravity = Gravity.END
                }

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemViewBinding =
            ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataChat = listMessage[position]
        holder.setIsRecyclable(false)
        holder.bind(dataChat)


    }

    override fun getItemCount(): Int = listMessage.size
}