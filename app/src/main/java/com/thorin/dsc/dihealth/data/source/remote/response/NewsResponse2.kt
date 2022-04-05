package com.thorin.dsc.dihealth.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class NewsResponse2 (

    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("posts")
    val posts: List<NewsResponse3>? = null

)