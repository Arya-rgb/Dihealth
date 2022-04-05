package com.thorin.dsc.dihealth.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetUserPostResponse(
    var email_user: String? = "",
    var nama_user: String? = "",
    var photo_url: String? = "",
    var post: String? = "",
    var uid: String? = ""

): Parcelable
