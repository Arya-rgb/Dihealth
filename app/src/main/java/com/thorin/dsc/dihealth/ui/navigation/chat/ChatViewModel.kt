package com.thorin.dsc.dihealth.ui.navigation.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thorin.dsc.dihealth.data.DihealthRepository
import com.thorin.dsc.dihealth.data.source.remote.response.ChatResponse
import com.thorin.dsc.dihealth.data.source.remote.response.ListUserResponse
import com.thorin.dsc.dihealth.data.source.remote.response.MessageListResponse

class ChatViewModel(private val dihealthRepository: DihealthRepository) : ViewModel() {

    fun getListUser(): LiveData<List<ListUserResponse>> = dihealthRepository.getListUser()

    fun uploadChat(
        message: String,
        profileUrl: String,
        chatId: String,
        username: String,
        uid: String
    ): LiveData<ChatResponse> =
        dihealthRepository.uploadDataChat(message, profileUrl, chatId, username, uid)

    fun getListMessage(chatId: String): LiveData<List<MessageListResponse>> =
        dihealthRepository.getListMessage(chatId)

    fun getUserMessage(chatId: String, query: String): LiveData<List<MessageListResponse>> =
        dihealthRepository.getUserMessage(chatId, query)

}