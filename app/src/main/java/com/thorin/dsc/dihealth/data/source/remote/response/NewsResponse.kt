package com.thorin.dsc.dihealth.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class NewsResponse(

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: Boolean? = null,

    @field:SerializedName("data")
    val data: NewsResponse2? = null
)