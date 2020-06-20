package es.gob.covidradar.datamanager.api

import es.gob.covidradar.models.request.RequestPostAnswers
import es.gob.covidradar.models.response.ResponseQuestions
import es.gob.covidradar.models.response.ResponseSettings
import es.gob.covidradar.models.response.ResponseUuid
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

    @GET("/configuration/settings")
    fun getSettings(): Call<ResponseSettings>

    @GET("/questionnaire/questions")
    fun getQuestions(): Call<ResponseQuestions>

    @POST("/questionnaire/answers")
    fun postAnswers(
        @Header(SEDIA_USER_TOKEN) uuid: String,
        @Body body: RequestPostAnswers
    ): Call<Unit>

}