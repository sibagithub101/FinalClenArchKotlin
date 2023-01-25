package com.kotlin.finalclenarchkotlin.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetResponseJackJill(
    @Expose
    @SerializedName("hello")
    val hello: String?
)

data class LoginReqModel(
    @Expose
    @SerializedName("username")
    val username: String? ,
    @Expose
    @SerializedName("password")
    val password: String?
)
data class LoginRespModel(
    @Expose
    @SerializedName("token")
    val token: String? ,
    @Expose
    @SerializedName("adminName")
    val adminName: String?
)