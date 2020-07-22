package es.gob.radarcovid.datamanager.api

import es.gob.radarcovid.models.response.ResponseLabels
import es.gob.radarcovid.models.response.ResponseLanguages
import es.gob.radarcovid.models.response.ResponseRegions
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ContentfulInterface {

    @GET("entries?access_token=Dv96LuZTUWSVMI_nxl59g30RPqi7TTeYkEr-UC4L6Qs&select=fields&content_type=androidData")
    fun getLabels(
        @Query("locale") language: String,
        @Query("fields.ccaa") region: String
    ): Call<ResponseLabels>

    @GET("entries?access_token=Dv96LuZTUWSVMI_nxl59g30RPqi7TTeYkEr-UC4L6Qs&select=fields.id,fields.description&locale=es-ES&content_type=masterDataType&fields.category%5bin%5d=locale&order=fields.description")
    fun getLanguages(): Call<ResponseLanguages>

    @GET("entries?access_token=Dv96LuZTUWSVMI_nxl59g30RPqi7TTeYkEr-UC4L6Qs&select=fields.id,fields.description&locale=es-ES&content_type=masterDataType&fields.category%5bin%5d=ccaa&order=fields.description")
    fun getRegions(): Call<ResponseRegions>

}