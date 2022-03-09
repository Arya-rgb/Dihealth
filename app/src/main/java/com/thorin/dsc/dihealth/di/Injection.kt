package com.thorin.dsc.dihealth.di

import android.content.Context
import com.thorin.dsc.dihealth.data.DihealthRepository
import com.thorin.dsc.dihealth.data.source.remote.RemoteDataSource

object Injection {

    fun provideRepository(context: Context): DihealthRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
        return DihealthRepository.getInstance(remoteDataSource)
    }

}