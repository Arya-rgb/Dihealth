package com.thorin.dsc.dihealth.ui.navigation.profil

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thorin.dsc.dihealth.data.DihealthRepository
import com.thorin.dsc.dihealth.data.source.remote.response.UserDataResponse

class ProfilViewModel(private val dihealthRepository: DihealthRepository): ViewModel() {

    fun getDataUser(): LiveData<UserDataResponse> = dihealthRepository.getDataProfileUser()

}