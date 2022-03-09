package com.thorin.dsc.dihealth.data

import androidx.lifecycle.LiveData
import com.thorin.dsc.dihealth.data.source.remote.response.UserDataResponse

interface DihealthDataSource {

 fun getUploadDataUser(uid:String, nama: String, email: String): LiveData<UserDataResponse>

}