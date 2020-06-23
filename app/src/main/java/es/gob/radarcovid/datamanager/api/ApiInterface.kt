package es.gob.radarcovid.datamanager.api

import es.gob.radarcovid.models.request.RequestKpiReport
import es.gob.radarcovid.models.request.RequestPostAnswers
import es.gob.radarcovid.models.response.ResponseQuestions
import es.gob.radarcovid.models.response.ResponseSettings
import es.gob.radarcovid.models.response.ResponseUuid
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

    @POST("/kpi-controller/kpi")
    fun postKpiReport(@Body body: RequestKpiReport): Call<Unit>

}