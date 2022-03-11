package com.thorin.dsc.dihealth.viewmodel.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thorin.dsc.dihealth.data.DihealthRepository
import com.thorin.dsc.dihealth.di.Injection
import com.thorin.dsc.dihealth.ui.editprofile.EditProfileViewModel
import com.thorin.dsc.dihealth.ui.navigation.profil.ProfilViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(private val mDihealthRepository: DihealthRepository): ViewModelProvider.NewInstanceFactory() {

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply{
                    instance = this
                }
            }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                EditProfileViewModel(mDihealthRepository) as T
            }
            modelClass.isAssignableFrom(ProfilViewModel::class.java) -> {
                ProfilViewModel(mDihealthRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class : "+modelClass.name)
        }
    }
}