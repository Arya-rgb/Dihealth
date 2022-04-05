package com.thorin.dsc.dihealth.ui.navigation.home.detail

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.thorin.dsc.dihealth.R
import com.thorin.dsc.dihealth.data.source.remote.response.ArtikelResponse
import com.thorin.dsc.dihealth.databinding.ActivityDetailArticleBinding


class DetailArticleActivity : AppCompatActivity() {

    private var _binding: ActivityDetailArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = " "
        actionBar?.title = " "
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val detailArticle = intent.getParcelableExtra<ArtikelResponse>(EXTRA_ARTICLES) as ArtikelResponse

        with(binding) {
            Glide.with(this@DetailArticleActivity)
                .load(detailArticle.photo_url)
                .placeholder(R.drawable.ic_default_profile)
                .into(imgHeader)

            textArticle.text = detailArticle.isi_artikel
        }

    }

    companion object {
        const val EXTRA_ARTICLES = "extra_articles"
    }

}