package com.thorin.dsc.dihealth.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserPostResponse(
    var photoUrl: String? = "",
    var uid: String? = "",
    var nama: String? = "",
    var email: String? = "",
    var post: String? = ""

): Parcelable