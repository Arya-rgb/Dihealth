package com.thorin.dsc.dihealth.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatResponse(
    var isi_pesan: String? = "",
    var profile_photo: String? = "",
    var chat_id: String? = "",
    var username: String? = ""
): Parcelable