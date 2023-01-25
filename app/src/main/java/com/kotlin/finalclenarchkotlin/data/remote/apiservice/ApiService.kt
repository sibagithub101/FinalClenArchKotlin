package com.kotlin.finalclenarchkotlin.data.remote.apiservice

import com.kotlin.finalclenarchkotlin.data.remote.models.GetResponseJackJill
import com.kotlin.finalclenarchkotlin.data.remote.models.LoginReqModel
import com.kotlin.finalclenarchkotlin.data.remote.models.LoginRespModel
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @Headers("Accept:application/json", "Content-Type:application/json")
    /**
     * Login JackJill API Call
     */
    @GET("generate/v1")
    suspend fun callLoginApiJackJillApi(): Response<GetResponseJackJill>

    @POST
    suspend fun requestForLogin(@Body param: LoginReqModel, @Url url: String): Response<LoginRespModel>

}