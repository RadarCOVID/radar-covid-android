package com.indra.contacttracing.datamanager.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/")
    fun test():Call<String>

}