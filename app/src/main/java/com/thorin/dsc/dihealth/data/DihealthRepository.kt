package com.thorin.dsc.dihealth.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thorin.dsc.dihealth.data.source.remote.RemoteDataSource
import com.thorin.dsc.dihealth.data.source.remote.response.*

class DihealthRepository private constructor(private val remoteDataSource: RemoteDataSource) :
    DihealthDataSource {

    companion object {
        @Volatile
        private var instance: DihealthRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): DihealthRepository =
            instance ?: synchronized(this) {
                DihealthRepository(remoteDataSource).apply { instance = this }
            }
    }

    override fun getUploadDataUser(
        photoUrl: String,
        uid: String,
        nama: String,
        email: String
    ): LiveData<UserDataResponse> {
        val userDataResult = MutableLiveData<UserDataResponse>()

        remoteDataSource.getUploadDataUser(
            photoUrl,
            uid,
            nama,
            email,
            object : RemoteDataSource.LoadDataUserCallback {
                override fun onDataUserReceived(userDataResponse: UserDataResponse) {
                    userDataResult.postValue(userDataResponse)
                }

            })

        return userDataResult

    }

    override fun getDataProfileUser(): LiveData<UserDataResponse> {
        val userProfileResult = MutableLiveData<UserDataResponse>()

        remoteDataSource.getDataUser(object : RemoteDataSource.LoadDataProfileCallback {
            override fun onDataProfileReceived(userDataResponse: UserDataResponse) {
                userProfileResult.postValue(userDataResponse)
            }

        })

        return userProfileResult

    }

    override fun getDataUploadPost(
        photoUrl: String,
        uid: String,
        nama: String,
        email: String,
        post: String
    ): LiveData<UserPostResponse> {

        val userPostResult = MutableLiveData<UserPostResponse>()

        remoteDataSource.getUploadDataPost(
            photoUrl,
            uid,
            nama,
            email,
            post,
            object : RemoteDataSource.LoadDataUploadPostCallBack {
                override fun onDataPostReceived(userPostResponse: UserPostResponse) {
                    userPostResult.postValue(userPostResponse)
                }

            })

        return userPostResult

    }

    override fun getDataPostUser(): LiveData<List<GetUserPostResponse>> {

        val dataPostUserResult = MutableLiveData<List<GetUserPostResponse>>()
        remoteDataSource.getDataUserPost(object : RemoteDataSource.LoadDataPostUser {

            override fun onDataPostUserReceived(getUserPostResponse: List<GetUserPostResponse>) {
                val dataPostUser = ArrayList<GetUserPostResponse>()
                for (response in getUserPostResponse) {
                    val dataPostUsers = GetUserPostResponse(
                        response.email_user,
                        response.nama_user,
                        response.photo_url,
                        response.post,
                        response.uid
                    )
                    dataPostUser.add(dataPostUsers)
                }
                dataPostUserResult.postValue(dataPostUser)

            }

        })

        return dataPostUserResult

    }

    override fun getDataArticle(): LiveData<List<ArtikelResponse>> {

        val dataArticleResult = MutableLiveData<List<ArtikelResponse>>()
        remoteDataSource.getDataArticle(object : RemoteDataSource.LoadDataArticle {
            override fun onDataArticleReceived(getArticle: List<ArtikelResponse>) {

                val dataArticle = ArrayList<ArtikelResponse>()
                for (response in getArticle) {
                    val dataArticles = ArtikelResponse(
                        response.id_artikel,
                        response.isi_artikel,
                        response.photo_url,
                        response.sumber,
                        response.tanggal_post,
                        response.title_artikel
                    )
                    dataArticle.add(dataArticles)
                }
                dataArticleResult.postValue(dataArticle)

            }


        })

        return dataArticleResult

    }

    override fun getQuotesData(): LiveData<QuotesResponse> {
        val userQuotesResult = MutableLiveData<QuotesResponse>()

        remoteDataSource.getDataQuotes(object : RemoteDataSource.LoadDataQuotesCallback {

            override fun onDataQuotesReceived(quotesResponse: QuotesResponse) {
                userQuotesResult.postValue(quotesResponse)
            }

        })
        return userQuotesResult
    }

    override fun getListUser(): LiveData<List<ListUserResponse>> {

        val dataListUserResult = MutableLiveData<List<ListUserResponse>>()
        remoteDataSource.getListUser(object : RemoteDataSource.LoadListUserCallback {
            override fun onDataListUserReceived(getListUser: List<ListUserResponse>) {

                val dataList = ArrayList<ListUserResponse>()
                for (response in getListUser) {
                    val dataListUser = ListUserResponse(
                        response.email_user,
                        response.nama_responden,
                        response.photo_url,
                        response.uid
                    )
                    dataList.add(dataListUser)
                }
                dataListUserResult.postValue(dataList)

            }


        })

        return dataListUserResult

    }

    override fun uploadDataChat(
        message: String,
        profileUrl: String,
        chatId: String,
        username: String,
        uid: String
    ): LiveData<ChatResponse> {

        val userChatResult = MutableLiveData<ChatResponse>()

        remoteDataSource.uploadChatData(
            message,
            profileUrl,
            chatId,
            username,
            uid,
            object : RemoteDataSource.UploadDataChatCallback {
                override fun onUploadDataUserReceived(chatResponse: ChatResponse) {
                    userChatResult.postValue(chatResponse)
                }

            })

        return userChatResult
    }

    override fun getListMessage(chatId: String): LiveData<List<MessageListResponse>> {

        val dataChatResult = MutableLiveData<List<MessageListResponse>>()
        remoteDataSource.getListMessage(chatId, object : RemoteDataSource.LoadListMessageCallback {
            override fun onDataMessageReceived(getListUser: List<MessageListResponse>) {
                val dataChatList = ArrayList<MessageListResponse>()
                for (response in getListUser) {
                    val dataChatListAdd = MessageListResponse(
                        response.chatId,
                        response.message,
                        response.profileUrl,
                        response.username,
                        response.uid
                    )
                    dataChatList.add(dataChatListAdd)
                }
                dataChatResult.postValue(dataChatList)
            }

        })

        return dataChatResult

    }

    override fun getUserMessage(
        chatId: String,
        query: String
    ): LiveData<List<MessageListResponse>> {

        val dataChatResult = MutableLiveData<List<MessageListResponse>>()
        remoteDataSource.getUserMessage(chatId, query, object : RemoteDataSource.LoadUserMessage {
            override fun onUserMessageReceived(listMessage: List<MessageListResponse>) {
                val dataChatList = ArrayList<MessageListResponse>()
                for (response in listMessage) {
                    val dataChatListAdd = MessageListResponse(
                        response.chatId,
                        response.message,
                        response.profileUrl,
                        response.username
                    )
                    dataChatList.add(dataChatListAdd)
                }
                dataChatResult.postValue(dataChatList)
            }

        })

        return dataChatResult

    }


}