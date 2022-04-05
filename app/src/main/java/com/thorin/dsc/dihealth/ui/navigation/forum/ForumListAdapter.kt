package com.thorin.dsc.dihealth.ui.navigation.forum

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.data.source.remote.response.GetUserPostResponse
import com.thorin.dsc.dihealth.databinding.ItemForumBinding

class ForumListAdapter : RecyclerView.Adapter<ForumListAdapter.ViewHolder>() {

    private var listPost = ArrayList<GetUserPostResponse>()

    fun setDataPost(dataPost: List<GetUserPostResponse>?) {

        if (dataPost != null) {
            this.listPost.clear()
            this.listPost.addAll(dataPost)
        }

    }


    class ViewHolder(private val binding: ItemForumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataPost: GetUserPostResponse) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(dataPost.photo_url)
                    .placeholder(R.drawable.ic_default_profile)
                    .into(imgProfil)
                idName.text = dataPost.nama_user
                idPost.text = dataPost.post
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemViewBinding =
            ItemForumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataChat = listPost[position]
        holder.bind(dataChat)
    }

    override fun getItemCount(): Int = listPost.size
}