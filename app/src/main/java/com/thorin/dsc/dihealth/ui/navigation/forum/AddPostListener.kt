package com.thorin.dsc.dihealth.ui.navigation.forum

import com.thorin.dsc.dihealth.data.source.remote.response.UserPostResponse

interface AddPostListener {

    fun onAddButtonClicked(item: UserPostResponse)

}