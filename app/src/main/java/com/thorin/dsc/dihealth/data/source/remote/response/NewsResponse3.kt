package com.thorin.dsc.dihealth.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class NewsResponse3 (

    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("pubDate")
    val pubDate: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("thumbnail")
    val thumbnail: String? = null,

    )