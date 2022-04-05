package com.thorin.dsc.dihealth.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuotesResponse(
    var photo: String = "",
    var quotes: String? = "",
    var quotes_id: String? = "",
): Parcelable