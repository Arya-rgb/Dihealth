package com.thorin.dsc.dihealth.ui.navigation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thorin.dsc.dihealth.data.DihealthRepository
import com.thorin.dsc.dihealth.data.source.remote.response.ArtikelResponse
import com.thorin.dsc.dihealth.data.source.remote.response.GetUserPostResponse
import com.thorin.dsc.dihealth.data.source.remote.response.QuotesResponse

class HomeViewModel(private val dihealthRepository: DihealthRepository) : ViewModel() {

    fun getArticleData(): LiveData<List<ArtikelResponse>> = dihealthRepository.getDataArticle()

    fun getQuotes(): LiveData<QuotesResponse> = dihealthRepository.getQuotesData()

}