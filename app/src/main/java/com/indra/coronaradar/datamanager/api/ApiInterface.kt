package com.indra.coronaradar.datamanager.api

import com.indra.coronaradar.models.response.ResponseSettings
import com.indra.coronaradar.models.response.ResponseUuid
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/")
    fun test(): Call<String>

    @GET("/token/uuid")
    fun getUuid(): Call<ResponseUuid>

    @GET("/settings")
    fun getSettings(): Call<ResponseSettings>

}