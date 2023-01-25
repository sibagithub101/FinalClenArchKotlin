package com.kotlin.finalclenarchkotlin.domain.repository

import com.kotlin.finalclenarchkotlin.data.remote.models.GetResponseJackJill
import com.kotlin.finalclenarchkotlin.data.remote.models.LoginReqModel
import com.kotlin.finalclenarchkotlin.data.remote.models.LoginRespModel
import com.kotlin.finalclenarchkotlin.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface LoginRepo {
    suspend fun loginJakJillApi(): Flow<ApiResult<GetResponseJackJill>>

    suspend fun loginApiCall(loginRequest: LoginReqModel, url: String) : Flow<ApiResult<LoginRespModel>>



}