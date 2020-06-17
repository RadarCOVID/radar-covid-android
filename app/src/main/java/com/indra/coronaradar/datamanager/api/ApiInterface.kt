package com.indra.coronaradar.datamanager.api

import com.indra.coronaradar.models.response.ResponseQuestions
import com.indra.coronaradar.models.response.ResponseSettings
import com.indra.coronaradar.models.response.ResponseUuid
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    companion object {

        const val SEDIA_USER_TOKEN = "SEDIA-UserToken"

    }

    @GET("/")
    fun test(): Call<String>

    @GET("/configuration/token/uuid")
    fun getUuid(): Call<ResponseUuid>

    @GET("/settings")
    fun getSettings(): Call<ResponseSettings>

    @GET("/questionnaire/questions")
    fun getQuestions(): Call<ResponseQuestions>

    @POST("/questionnaire/answers")
    fun postAnswers(
        @Header(SEDIA_USER_TOKEN) uuid: String,
        @Body body: HashMap<String, String>
    ): Call<ResponseQuestions>

}