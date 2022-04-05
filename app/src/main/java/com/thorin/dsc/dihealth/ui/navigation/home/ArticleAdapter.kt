package com.thorin.dsc.dihealth.ui.navigation.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thorin.dsc.dihealth.data.source.remote.response.ArtikelResponse
import com.thorin.dsc.dihealth.databinding.ItemSlideHomeBinding
import com.thorin.dsc.dihealth.ui.navigation.home.detail.DetailArticleActivity

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private var listArticle = ArrayList<ArtikelResponse>()

    fun setDataArticle(dataArticle: List<ArtikelResponse>?) {

        if (dataArticle != null) {
            this.listArticle.clear()
            this.listArticle.addAll(dataArticle)
        }

    }

    class ViewHolder(private val binding: ItemSlideHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataArticle: ArtikelResponse) {

            with(binding) {
                Glide.with(itemView.context)
                    .load(dataArticle.photo_url)
                    .into(ivItemImage)
                tvItemTitle.text = dataArticle.title_artikel
                tvItemSubtitle.text = dataArticle.tanggal_post
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemViewBinding =
            ItemSlideHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemViewBinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataArticles = listArticle[position]
        holder.bind(dataArticles)

        val dataArticle = ArtikelResponse(
            dataArticles.id_artikel,
            dataArticles.isi_artikel,
            dataArticles.photo_url,
            dataArticles.sumber,
            dataArticles.tanggal_post,
            dataArticles.title_artikel
        )

        holder.itemView.setOnClickListener {
            val moveArticleDetail = Intent(holder.itemView.context, DetailArticleActivity::class.java)
            moveArticleDetail.putExtra(DetailArticleActivity.EXTRA_ARTICLES, dataArticle)
            holder.itemView.context.startActivity(moveArticleDetail)
        }

    }

    override fun getItemCount(): Int = listArticle.size

}