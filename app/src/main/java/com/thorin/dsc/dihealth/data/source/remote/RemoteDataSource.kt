package com.thorin.dsc.dihealth.data.source.remote


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.thorin.dsc.dihealth.data.source.remote.response.UserDataResponse

class RemoteDataSource private constructor() {

    var statusGetUploadDataUser = MutableLiveData<Boolean?>()

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource().apply { instance = this }
            }
    }

    //upload data user

    fun getUploadDataUser(photoUrl: String, uid:String, nama: String, email: String, callback: LoadDataUserCallback ) {
        val refUser: DatabaseReference = FirebaseDatabase.getInstance().reference.child("data_user")
            .child(uid)
        val userHashMap = HashMap<String, Any>()
        userHashMap["photo_url"] = photoUrl
        userHashMap["nama_responden"] = nama
        userHashMap["email_user"] = email
        refUser.updateChildren(userHashMap)
            .addOnCompleteListener { tasks ->
                statusGetUploadDataUser.value = tasks.isSuccessful
            }
    }


    interface LoadDataUserCallback {
        fun onDataUserReceived(userDataResponse: UserDataResponse)
    }

    //get data user

    fun getDataUser(callback: LoadDataProfileCallback) {

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val list = ArrayList<UserDataResponse>()
        val reff: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("data_user").child(mAuth.currentUser?.uid.toString())
        reff.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val emailUser = snapshot.child("email_user").value.toString()
                    val namaUser = snapshot.child("nama_responden").value.toString()
                    val photoUrl = snapshot.child("photo_url").value.toString()

                    val retrieveDataUser = UserDataResponse(photoUrl, mAuth.currentUser?.uid.toString(), namaUser, emailUser)
                    callback.onDataProfileReceived(retrieveDataUser)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "error: " + error.message)
            }

        })

    }

    interface LoadDataProfileCallback {
        fun onDataProfileReceived(userDataResponse: UserDataResponse)
    }


}