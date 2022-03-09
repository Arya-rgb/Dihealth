package com.thorin.dsc.dihealth.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thorin.dsc.dihealth.data.source.remote.RemoteDataSource
import com.thorin.dsc.dihealth.data.source.remote.response.UserDataResponse

class DihealthRepository private constructor(private val remoteDataSource: RemoteDataSource) : DihealthDataSource {

    companion object {
        @Volatile
        private var instance: DihealthRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): DihealthRepository =
            instance ?: synchronized(this) {
                DihealthRepository(remoteDataSource).apply { instance = this }
            }
    }

    override fun getUploadDataUser(
        uid: String,
        nama: String,
        email: String
    ): LiveData<UserDataResponse> {
        val userDataResult = MutableLiveData<UserDataResponse>()

        remoteDataSource.getUploadDataUser(uid, nama, email, object : RemoteDataSource.LoadDataUserCallback {
            override fun onDataUserReceived(userDataResponse: UserDataResponse) {
                userDataResult.postValue(userDataResponse)
            }

        })

        return userDataResult

    }


}