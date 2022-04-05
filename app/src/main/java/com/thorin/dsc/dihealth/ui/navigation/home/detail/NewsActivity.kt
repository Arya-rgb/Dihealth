package com.thorin.dsc.dihealth.ui.navigation.home.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.thorin.dsc.dihealth.data.source.remote.response.NewsResponse
import com.thorin.dsc.dihealth.data.source.remote.response.NewsResponse2
import com.thorin.dsc.dihealth.data.source.remote.response.NewsResponse3
import com.thorin.dsc.dihealth.data.source.remote.retrofit.ApiConfig
import com.thorin.dsc.dihealth.databinding.ActivityNewsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivity : AppCompatActivity() {

    private var _binding: ActivityNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NewsAdapter(mutableListOf())

        with(binding) {
            rvNews.setHasFixedSize(true)
            rvNews.layoutManager = LinearLayoutManager(this@NewsActivity)
            rvNews.adapter = adapter
        }



        getNews()

    }

    private fun getNews() {
        val client = ApiConfig.getApiService().getNews()

        binding.progressBar.visibility = View.VISIBLE

        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    binding.progressBar.visibility = View.GONE
                    val dataArray = response.body()?.data as NewsResponse2
                    val responses = dataArray.posts as List<NewsResponse3>
                    for (data in responses) {
                        adapter.addUser(data)
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(this@NewsActivity, t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
                binding.progressBar.visibility = View.GONE
            }
        })
    }

}