package com.thorin.dsc.dihealth.data.source.remote.retrofit

import com.thorin.dsc.dihealth.data.source.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("tribun/kesehatan/")
    fun getNews(): Call<NewsResponse>

}