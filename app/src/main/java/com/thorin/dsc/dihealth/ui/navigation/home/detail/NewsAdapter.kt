package com.thorin.dsc.dihealth.ui.navigation.home.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.data.source.remote.response.NewsResponse3

class NewsAdapter(private val news: MutableList<NewsResponse3>) : RecyclerView.Adapter<NewsAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_slide_home, parent, false)
        return ListViewHolder(
            view
        )
    }

    fun addUser(newUsers: NewsResponse3) {
        news.add(newUsers)
        notifyItemInserted(news.lastIndex)
    }


    override fun onBindViewHolder(holder: NewsAdapter.ListViewHolder, position: Int) {
        val news = news[position]

        Glide.with(holder.itemView.context)
            .load(news.thumbnail)
            .into(holder.ivAvatar)

        holder.tvUserTitle.text = news.title
        holder.tvUserDescription.text = news.description
    }

    override fun getItemCount(): Int = news.size


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUserTitle: TextView = itemView.findViewById(R.id.tv_item_title)
        var tvUserDescription: TextView = itemView.findViewById(R.id.tv_item_subtitle)
        var ivAvatar: ImageView = itemView.findViewById(R.id.iv_item_image)

    }


}