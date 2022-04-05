package com.thorin.dsc.dihealth.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageListResponse(
    var chatId: String = "",
    var message: String? = "",
    var profileUrl: String? = "",
    var username: String? = "",
    var uid: String? = ""
): Parcelable