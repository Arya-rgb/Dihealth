package com.thorin.dsc.dihealth.ui.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thorin.dsc.dihealth.data.DihealthRepository
import com.thorin.dsc.dihealth.data.source.remote.response.UserDataResponse

class EditProfileViewModel(private val dihealthRepository: DihealthRepository) : ViewModel() {

    fun uploadDataUser(
        photoUrl: String,
        uid: String,
        nama: String,
        email: String
    ): LiveData<UserDataResponse> =
        dihealthRepository.getUploadDataUser(photoUrl, uid, nama, email)

}