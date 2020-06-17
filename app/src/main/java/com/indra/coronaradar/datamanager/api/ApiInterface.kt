package com.indra.coronaradar.datamanager.api

import com.indra.coronaradar.models.response.ResponseQuestions
import com.indra.coronaradar.models.response.ResponseSettings
import com.indra.coronaradar.models.response.ResponseUuid
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/")
    fun test(): Call<String>

    @GET("/configuration/token/uuid")
    fun getUuid(): Call<ResponseUuid>

    @GET("/settings")
    fun getSettings(): Call<ResponseSettings>

    @GET("/questionnaire/questions")
    fun getQuestions(): Call<ResponseQuestions>

}