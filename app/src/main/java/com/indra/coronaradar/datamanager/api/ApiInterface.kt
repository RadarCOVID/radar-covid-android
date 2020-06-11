package com.indra.coronaradar.datamanager.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/")
    fun test():Call<String>

}