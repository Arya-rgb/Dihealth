package com.thorin.dsc.dihealth.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thorin.dsc.dihealth.data.source.remote.response.*

interface DihealthDataSource {

 fun getUploadDataUser(photoUrl: String, uid:String, nama: String, email: String): LiveData<UserDataResponse>

 fun getDataProfileUser(): LiveData<UserDataResponse>

 fun getDataUploadPost(photoUrl: String, uid: String, nama: String, email: String, post: String): LiveData<UserPostResponse>

 fun getDataPostUser(): LiveData<List<GetUserPostResponse>>

 fun getDataArticle(): LiveData<List<ArtikelResponse>>

 fun getQuotesData(): LiveData<QuotesResponse>

 fun getListUser(): LiveData<List<ListUserResponse>>

 fun uploadDataChat(message: String, profileUrl:String, chatId: String, username: String, uid: String): LiveData<ChatResponse>

 fun getListMessage(chatId: String): LiveData<List<MessageListResponse>>

 fun getUserMessage(chatId: String, query: String) : LiveData<List<MessageListResponse>>

}