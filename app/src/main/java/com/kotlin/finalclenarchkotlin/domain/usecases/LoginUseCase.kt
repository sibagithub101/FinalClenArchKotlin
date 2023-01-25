package com.kotlin.finalclenarchkotlin.domain.usecases

import com.kotlin.finalclenarchkotlin.data.remote.models.GetResponseJackJill
import com.kotlin.finalclenarchkotlin.data.remote.models.LoginReqModel
import com.kotlin.finalclenarchkotlin.data.remote.models.LoginRespModel
import com.kotlin.finalclenarchkotlin.domain.repository.LoginRepo
import com.kotlin.finalclenarchkotlin.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository:LoginRepo) {
    suspend fun invoke2(): Flow<ApiResult<GetResponseJackJill>> {
        return repository.loginJakJillApi()
    }

    suspend fun invoke(loginReqModel: LoginReqModel, url: String): Flow<ApiResult<LoginRespModel>> {
        return repository.loginApiCall(loginReqModel, url)
    }
}