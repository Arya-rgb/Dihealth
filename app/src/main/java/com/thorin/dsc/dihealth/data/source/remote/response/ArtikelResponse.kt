package com.thorin.dsc.dihealth.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtikelResponse(
    var id_artikel: String? = "",
    var isi_artikel: String? = "",
    var photo_url: String? = "",
    var sumber: String? = "",
    var tanggal_post: String? = "",
    var title_artikel: String? = ""
): Parcelable
