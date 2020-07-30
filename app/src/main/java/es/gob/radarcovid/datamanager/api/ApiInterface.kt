package es.gob.radarcovid.datamanager.api

import es.gob.radarcovid.models.request.RequestKpiReport
import es.gob.radarcovid.models.request.RequestPostAnswers
import es.gob.radarcovid.models.request.RequestVerifyCode
import es.gob.radarcovid.models.response.*
import retrofit2.Call
import retrofit2.http.*

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

    @POST("/kpi/kpi")
    fun postKpiReport(@Body body: RequestKpiReport): Call<Unit>

    @GET("/configuration/texts/{ccaa}")
    fun getLabels(
        @Path("ccaa") region: String,
        @Query("locale") language: String
    ): Call<ResponseLabels>

    @GET("/configuration/masterData/locales")
    fun getLanguages(@Query("locale") language: String): Call<ResponseLanguages>

    @GET("/configuration/masterData/ccaa")
    fun getRegions(@Query("locale") language: String): Call<ResponseRegions>

    @POST("/verification/verify/code")
    fun verifyCode(@Body body: RequestVerifyCode): Call<ResponseToken>


}