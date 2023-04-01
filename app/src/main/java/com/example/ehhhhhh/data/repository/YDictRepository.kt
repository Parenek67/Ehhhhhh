package com.example.ehhhhhh.data.repository

import com.example.ehhhhhh.data.api.YDictInstance
import com.example.ehhhhhh.data.model.ydictapi.YDictResponse
import retrofit2.Response

class YDictRepository {

    suspend fun getWord(key: String, lang: String, text: String): Response<YDictResponse> {
        return YDictInstance.api.getWord(key, lang, text)
    }
}