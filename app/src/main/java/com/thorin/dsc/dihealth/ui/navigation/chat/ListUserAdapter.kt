package com.thorin.dsc.dihealth.ui.navigation.chat

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.data.source.remote.response.ChatResponse
import com.thorin.dsc.dihealth.data.source.remote.response.ListUserResponse
import com.thorin.dsc.dihealth.databinding.ItemListUserBinding

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    private var listUser = ArrayList<ListUserResponse>()

    fun setDataUser(dataUser: List<ListUserResponse>?) {

        if (dataUser != null) {
            this.listUser.clear()
            this.listUser.addAll(dataUser)
        }
    }

    class ViewHolder(private val binding: ItemListUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dataUser: ListUserResponse) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(dataUser.photo_url)
                    .placeholder(R.drawable.ic_default_profile)
                    .into(imgProfil)

                idName.text = dataUser.nama_responden



            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserAdapter.ViewHolder {

        val itemViewBinding =
            ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemViewBinding)

    }

    override fun onBindViewHolder(holder: ListUserAdapter.ViewHolder, position: Int) {

        val dataUser = listUser[position]
        holder.bind(dataUser)

        val dataChat = ListUserResponse(
            dataUser.email_user,
            dataUser.nama_responden,
            dataUser.photo_url,
            dataUser.uid
        )

        holder.itemView.setOnClickListener {
            val moveToChat = Intent(holder.itemView.context, ChatRoomActivity::class.java)
            moveToChat.putExtra(ChatRoomActivity.EXTRA_CHAT, dataChat)
            holder.itemView.context.startActivity(moveToChat)
        }

    }

    override fun getItemCount(): Int = listUser.size

}