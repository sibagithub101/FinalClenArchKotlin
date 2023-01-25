package com.kotlin.finalclenarchkotlin.data.repository


import com.kotlin.finalclenarchkotlin.data.remote.apiservice.ApiService
import com.kotlin.finalclenarchkotlin.data.remote.models.GetResponseJackJill
import com.kotlin.finalclenarchkotlin.data.remote.models.LoginReqModel
import com.kotlin.finalclenarchkotlin.data.remote.models.LoginRespModel
import com.kotlin.finalclenarchkotlin.domain.repository.LoginRepo
import com.kotlin.finalclenarchkotlin.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepoImpl @Inject constructor(private val loginApiService: ApiService):LoginRepo {
    override suspend fun loginJakJillApi(): Flow<ApiResult<GetResponseJackJill>> {
        return flow {
            val response = loginApiService.callLoginApiJackJillApi()
            if(response.isSuccessful){
                val loginJakJillResp= GetResponseJackJill(response.body()?.hello)
                emit(ApiResult.Success(loginJakJillResp))
            }else{
               emit(ApiResult.Error(response.message()))
            }
        }
    }

    override suspend fun loginApiCall(
        loginRequest: LoginReqModel,
        url: String
    ): Flow<ApiResult<LoginRespModel>> {
      return flow {
          val response = loginApiService.requestForLogin(loginRequest,url)
          if(response.isSuccessful){
            val loginReq = LoginRespModel(response.body()?.token,response.body()?.adminName)
              emit(ApiResult.Success(loginReq))
          }else{
              val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) }
              val message =
                  if (jsonObject?.has("message") == true) jsonObject.getString("message") else "${response.code()} Error"
              emit(ApiResult.Error(message))
          }
      }
    }

}