package com.kotlin.finalclenarchkotlin.presentation.ui

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.kotlin.finalclenarchkotlin.data.remote.models.LoginReqModel
import com.kotlin.finalclenarchkotlin.databinding.ActivityMainBinding
import com.kotlin.finalclenarchkotlin.presentation.viewModels.LoginActivityState
import com.kotlin.finalclenarchkotlin.presentation.viewModels.LoginViewModel
import com.kotlin.finalclenarchkotlin.presentation.viewModels.ValidationState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  lateinit var progressDialog:ProgressDialog
    private var userName = ""
    private var password = ""
    private val loginViewModel:LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)

        apiObserve()
        validationObserver()

        binding.btnLogin.setOnClickListener {
            loginViewModel.setEmailAndPassword(binding.etUSerName,binding.etPassword)
        }
    }

    private fun apiObserve(){
        loginViewModel.mState
            .flowWithLifecycle(lifecycle,  Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(lifecycleScope)

        loginViewModel.mState
            .flowWithLifecycle(lifecycle,Lifecycle.State.STARTED)
            .onEach { state2 -> handleLoginApiCall(state2) }
            .launchIn(lifecycleScope)
    }

    private fun validationObserver() {
        loginViewModel.mvalidateState.observe(this){result ->
            when(result){
                is ValidationState.IsValidSuccess ->{
                     userName = result.loginReq.username.toString()
                     password = result.loginReq.password.toString()
                    loginViewModel.loginJakcjillAPiCall()

                }
                is ValidationState.IsValid ->{
                    Toast.makeText(this,"Please Enter all field",Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun handleLoginApiCall(state2: LoginActivityState) {
        when(state2){
            LoginActivityState.Init -> Unit
            is LoginActivityState.IsLoading ->{
                progressDialog.setMessage("Please Wait...!!")
                progressDialog.setCancelable(false)
                progressDialog.show()
            }
            is LoginActivityState.SuccessLogin -> {
                progressDialog.dismiss()
                val admin = state2.loginEntity.adminName
                val token = state2.loginEntity.token
                Timber.tag(TAG).e("getSuccessLoginApi: " + admin + " + " + token)
                Toast.makeText(this, "SuccessFull Login", Toast.LENGTH_SHORT).show()

            }
            is LoginActivityState.Failure ->{
                progressDialog.dismiss()
                Toast.makeText(this,state2.failMsg,Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }

    }

    private fun handleStateChange(state: LoginActivityState) {

         when(state){
              LoginActivityState.Init -> Unit
             is LoginActivityState.IsLoading ->{
                 progressDialog.setMessage("Please Wait...!!")
                 progressDialog.setCancelable(false)
                 progressDialog.show()
             }
              is LoginActivityState.SuccessJackJill -> {
                  progressDialog.dismiss()
                  val hello = state.loginJakjIll.hello
                  Timber.tag(TAG).e("getSuccessJackJill: %s", hello)
                  val getEncodeData = hello.toString()
                  val data: ByteArray = Base64.decode(getEncodeData, Base64.DEFAULT)
                  val base64Decode = String(data, Charsets.UTF_8)
                  loginViewModel.loginAPiCall(LoginReqModel(userName, password), base64Decode)

              }
             is LoginActivityState.Failure ->{
                 progressDialog.dismiss()
                 Toast.makeText(this,state.failMsg,Toast.LENGTH_SHORT).show()
             }
             else -> {}
         }
    }
}
