package com.kotlin.finalclenarchkotlin.presentation.viewModels

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.finalclenarchkotlin.data.remote.models.GetResponseJackJill
import com.kotlin.finalclenarchkotlin.data.remote.models.LoginReqModel
import com.kotlin.finalclenarchkotlin.data.remote.models.LoginRespModel
import com.kotlin.finalclenarchkotlin.domain.usecases.LoginUseCase
import com.kotlin.finalclenarchkotlin.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase):ViewModel() {
    private val state = MutableStateFlow<LoginActivityState>(LoginActivityState.Init)
    val mState: StateFlow<LoginActivityState> get() = state

    private val validateState = MutableLiveData<ValidationState>(ValidationState.Init)
    val mvalidateState: LiveData<ValidationState> get() = validateState

    private fun setLoading(){
        state.value = LoginActivityState.IsLoading(true)
    }

    private fun hideLoading(){
        state.value = LoginActivityState.IsLoading(false)
    }

    private fun showToast(message: String){
        state.value = LoginActivityState.ShowToast(message)
    }

    fun loginJakcjillAPiCall(){
        viewModelScope.launch {
            loginUseCase.invoke2()
                .onStart {
                    setLoading()
                }
                .catch {exception ->
                    hideLoading()
                    showToast(exception.message.toString())
                }
                .collect { result ->
                    hideLoading()
                    when(result){
                        is ApiResult.Error -> state.value = LoginActivityState.Failure(result.message.toString())
                        is ApiResult.Success -> state.value = LoginActivityState.SuccessJackJill(result.data!!)
                        else -> {}
                    }
                }


        }
    }

    fun loginAPiCall(loginReqModel: LoginReqModel,url:String){
        viewModelScope.launch {
            loginUseCase.invoke(loginReqModel,url)
                .onStart {
                    setLoading()
                }
                .catch {exception ->
                    hideLoading()
                    showToast(exception.message.toString())
                }
                .collect { result ->
                    hideLoading()
                    when(result){
                        is ApiResult.Error -> state.value = LoginActivityState.Failure(result.message.toString())
                        is ApiResult.Success -> state.value = LoginActivityState.SuccessLogin(result.data!!)
                        else -> {}
                    }
                }


        }
    }

    fun setEmailAndPassword(etUSerName: EditText, etPassword: EditText) {
    val strUserName = etUSerName.text.toString()
        val strPassword = etPassword.text.toString()
        if(strUserName.isEmpty()){
            etUSerName.error = "Please Enter userName!"
            validateState.value = ValidationState.IsValid(false)
        }
        else if(strPassword.isEmpty()){
            etPassword.error = "Please Enter userName!"
            validateState.value = ValidationState.IsValid(false)
        }else{
            val loginReq = LoginReqModel(strUserName,strPassword)
            validateState.value = ValidationState.IsValidSuccess(loginReq)
        }
    }


}

sealed class LoginActivityState  {
    object Init : LoginActivityState()
    data class IsLoading(val isLoading: Boolean) : LoginActivityState()
    data class ShowToast(val message: String) : LoginActivityState()
    data class SuccessLogin (val loginEntity: LoginRespModel) : LoginActivityState()
    data class SuccessJackJill (val loginJakjIll: GetResponseJackJill) : LoginActivityState()
    class Failure (val failMsg: String) : LoginActivityState()
}

sealed class ValidationState{
    object Init : ValidationState()
    data class IsValidSuccess (val loginReq: LoginReqModel) : ValidationState()
    data class IsValid(val isLoading: Boolean) : ValidationState()
}

