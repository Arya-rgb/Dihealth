package com.thorin.dsc.dihealth.data.source.remote


import android.annotation.SuppressLint
import android.os.Messenger
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.thorin.dsc.dihealth.data.source.remote.response.*
import java.text.SimpleDateFormat
import java.util.*

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

    //upload data user-------------------------------------------------------------------------

    fun getUploadDataUser(photoUrl: String, uid:String, nama: String, email: String, callback: LoadDataUserCallback ) {
        val refUser: DatabaseReference = FirebaseDatabase.getInstance().reference.child("data_user")
            .child(uid)
        val userHashMap = HashMap<String, Any>()
        userHashMap["photo_url"] = photoUrl
        userHashMap["nama_responden"] = nama
        userHashMap["email_user"] = email
        userHashMap["uid"] = uid
        refUser.updateChildren(userHashMap)
            .addOnCompleteListener { tasks ->
                statusGetUploadDataUser.value = tasks.isSuccessful
            }
    }


    interface LoadDataUserCallback {
        fun onDataUserReceived(userDataResponse: UserDataResponse)
    }

    //-----------------------------------------------------------------------------------------------

    //get data user---------------------------------------------------------------------------------

    fun getDataUser(callback: LoadDataProfileCallback) {

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val list = ArrayList<UserDataResponse>()
        val reff: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("data_user")
                .child(mAuth.currentUser?.uid.toString())
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

    //-----------------------------------------------------------------------------------------------

    //upload post Data-------------------------------------------------------------------------------

    @SuppressLint("SimpleDateFormat")
    fun getUploadDataPost(
        photoUrl: String,
        uid: String,
        nama: String,
        email: String,
        post: String,
        callback: LoadDataUploadPostCallBack
    ) {

        val date = Date()
        val formatter = SimpleDateFormat("yyyyMMddHHmmssSSSSSS")
        val answer: String = formatter.format(date)

        val refUser: DatabaseReference =
            FirebaseDatabase.getInstance().reference.child("data_posting")
                .child(answer)
        val userHashMap = HashMap<String, Any>()
        userHashMap["photo_url"] = photoUrl
        userHashMap["nama_user"] = nama
        userHashMap["email_user"] = email
        userHashMap["uid"] = uid
        userHashMap["post"] = post
        refUser.updateChildren(userHashMap)
            .addOnCompleteListener { tasks ->
                statusGetUploadDataUser.value = tasks.isSuccessful
            }
    }


    interface LoadDataUploadPostCallBack {
        fun onDataPostReceived(userPostResponse: UserPostResponse)
    }

    //-----------------------------------------------------------------------------------------------

    //get data post user-----------------------------------------------------------------------------

    fun getDataUserPost(callback: LoadDataPostUser): List<GetUserPostResponse> {

        val list = ArrayList<GetUserPostResponse>()
        val reff: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("data_posting")
        reff.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val dataChat = dataSnapshot.getValue(GetUserPostResponse::class.java)
                        list.add(dataChat!!)
                        callback.onDataPostUserReceived(list)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "error: " + error.message)
            }

        })
        return list

    }

    interface LoadDataPostUser {
        fun onDataPostUserReceived(getUserPostResponse: List<GetUserPostResponse>)
    }

    //-----------------------------------------------------------------------------------------------

    //get Article Data-------------------------------------------------------------------------------

    fun getDataArticle(callback: LoadDataArticle): List<ArtikelResponse> {

        val list = ArrayList<ArtikelResponse>()
        val reff: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("data_artikel")
        reff.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val dataChat = dataSnapshot.getValue(ArtikelResponse::class.java)
                        dataChat?.let { list.add(it) }
                        callback.onDataArticleReceived(list)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "error: " + error.message)
            }

        })
        return list

    }

    interface LoadDataArticle {
        fun onDataArticleReceived(getArticle: List<ArtikelResponse>)
    }

    //-----------------------------------------------------------------------------------------------

    //get Quotes-------------------------------------------------------------------------------------

    fun getDataQuotes(callback: LoadDataQuotesCallback) {

        val reff: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("quotes")
        reff.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val photo = snapshot.child("photo").value.toString()
                    val quotes = snapshot.child("quotes").value.toString()
                    val quotesId = snapshot.child("photo_url").value.toString()

                    val retrieveDataQuotes = QuotesResponse(photo, quotes, quotesId)
                    callback.onDataQuotesReceived(retrieveDataQuotes)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "error: " + error.message)
            }

        })

    }

    interface LoadDataQuotesCallback {
        fun onDataQuotesReceived(quotesResponse: QuotesResponse)
    }

    //-----------------------------------------------------------------------------------------------

    //get List User----------------------------------------------------------------------------------

    fun getListUser(callback: LoadListUserCallback): List<ListUserResponse> {

        val list = ArrayList<ListUserResponse>()
        val reff: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("data_user")
        reff.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val dataChat = dataSnapshot.getValue(ListUserResponse::class.java)
                        dataChat?.let { list.add(it) }
                        callback.onDataListUserReceived(list)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "error: " + error.message)
            }

        })
        return list

    }

    interface LoadListUserCallback {
        fun onDataListUserReceived(getListUser: List<ListUserResponse>)
    }

    //-----------------------------------------------------------------------------------------------

    //upload chat------------------------------------------------------------------------------------
    @SuppressLint("SimpleDateFormat")
    fun uploadChatData(
        message: String,
        profileUrl: String,
        chatId: String,
        username: String,
        uid: String,
        callback: UploadDataChatCallback
    ) {

        val date = Date()
        val formatter = SimpleDateFormat("yyyyMMddHHmmssSSSSSS")
        val dateSend: String = formatter.format(date)

        val refUser: DatabaseReference =
            FirebaseDatabase.getInstance().reference.child("chat").child(chatId).child(dateSend)
        val userHashMap = HashMap<String, Any>()
        userHashMap["message"] = message
        userHashMap["profileUrl"] = profileUrl
        userHashMap["chatId"] = chatId
        userHashMap["username"] = username
        userHashMap["uid"] = uid
        refUser.updateChildren(userHashMap)
            .addOnCompleteListener { tasks ->
                statusGetUploadDataUser.value = tasks.isSuccessful
            }
    }

    interface UploadDataChatCallback {
        fun onUploadDataUserReceived(chatResponse: ChatResponse)
    }

    //get List Message-----------------------------------------------------------------------------------

    fun getListMessage(chatId: String, callback: LoadListMessageCallback): List<MessageListResponse> {

        val list = ArrayList<MessageListResponse>()
        val reff: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("chat").child(chatId)
        reff.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val dataChat = dataSnapshot.getValue(MessageListResponse::class.java)
                        dataChat?.let { list.add(it) }
                        callback.onDataMessageReceived(list)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "error: " + error.message)
            }

        })
        return list

    }

    interface LoadListMessageCallback {
        fun onDataMessageReceived(getListUser: List<MessageListResponse>)
    }

    //--------------------------------------------------------------------------------------------

    //get user message----------------------------------------------------------------------------

    fun getUserMessage(chatId: String, query: String, callback: LoadUserMessage) {
        val list = ArrayList<MessageListResponse>()
        val queryId = FirebaseDatabase.getInstance().reference
            .child("chat")
            .child(chatId).orderByChild(query)
            .startAt(query)
            .endAt(query + "\uf8ff")

        queryId.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(MessageListResponse::class.java)
                        list.clear()
                        user?.let { list.add(it) }
                        callback.onUserMessageReceived(list)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "hahay")
            }

        })

    }

    interface LoadUserMessage {
        fun onUserMessageReceived(listMessage: List<MessageListResponse>)
    }

    //--------------------------------------------------------------------------------------------

}