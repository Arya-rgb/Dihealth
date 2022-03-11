package com.thorin.dsc.dihealth.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thorin.dsc.dihealth.data.source.remote.response.UserDataResponse

interface DihealthDataSource {

 fun getUploadDataUser(photoUrl: String, uid:String, nama: String, email: String): LiveData<UserDataResponse>

 fun getDataProfileUser(): LiveData<UserDataResponse>

}