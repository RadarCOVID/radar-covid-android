package es.gob.radarcovid.datamanager.api

import es.gob.radarcovid.models.response.ResponseLabels
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ContentfulInterface {

    @GET("entries?access_token=Dv96LuZTUWSVMI_nxl59g30RPqi7TTeYkEr-UC4L6Qs&select=fields&content_type=androidData")
    fun getLabels(@Query("locale") locale: String): Call<ResponseLabels>

}