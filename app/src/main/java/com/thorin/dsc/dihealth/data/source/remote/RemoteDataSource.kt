package com.thorin.dsc.dihealth.data.source.remote

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thorin.dsc.dihealth.data.source.remote.response.UserDataResponse

class RemoteDataSource private constructor() {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource().apply { instance = this }
            }
    }

    fun getUploadDataUser(uid:String, nama: String, email: String, callback: LoadDataUserCallback ) {
        val refUser: DatabaseReference = FirebaseDatabase.getInstance().reference.child("data_user")
            .child(uid)
        val userHashMap = HashMap<String, Any>()
        userHashMap["nama_responden"] = nama
        userHashMap["alamat_responden"] = email
        refUser.updateChildren(userHashMap)
            .addOnCompleteListener { tasks ->
                if (tasks.isSuccessful) {
                } else {
                }
            }
    }

    interface LoadDataUserCallback {
        fun onDataUserReceived(userDataResponse: UserDataResponse)
    }

}