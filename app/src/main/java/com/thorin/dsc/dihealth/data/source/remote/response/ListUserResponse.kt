package com.thorin.dsc.dihealth.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListUserResponse(
    var email_user: String = "",
    var nama_responden: String? = "",
    var photo_url: String? = "",
    var uid: String? = ""
): Parcelable