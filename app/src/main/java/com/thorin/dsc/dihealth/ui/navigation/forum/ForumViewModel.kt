package com.thorin.dsc.dihealth.ui.navigation.forum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thorin.dsc.dihealth.data.DihealthRepository
import com.thorin.dsc.dihealth.data.source.remote.response.GetUserPostResponse
import com.thorin.dsc.dihealth.data.source.remote.response.UserDataResponse
import com.thorin.dsc.dihealth.data.source.remote.response.UserPostResponse

class ForumViewModel(private val dihealthRepository: DihealthRepository) : ViewModel() {

    fun getDataUploadPost(
        photoUrl: String,
        uid: String,
        nama: String,
        email: String,
        post: String
    ): LiveData<UserPostResponse> =
        dihealthRepository.getDataUploadPost(photoUrl, uid, nama, email, post)

    fun getPostData(): LiveData<List<GetUserPostResponse>> = dihealthRepository.getDataPostUser()

}