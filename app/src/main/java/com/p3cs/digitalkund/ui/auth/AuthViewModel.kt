package com.p3cs.digitalkund.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.p3cs.digitalkund.data.repos.AuthRepository
import com.p3cs.digitalkund.data.repos.baseRepo.RepositoryCallback
import com.p3cs.digitalkund.util.ApiException
import com.p3cs.digitalkund.util.Coroutines
import com.p3cs.digitalkund.util.NoInternetException


class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordconfirm: String? = null

    var authListener: AuthListener? = null

    fun currentUser() = repository.currentUser()


    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }
        Coroutines.main {
            try {
                repository.login(email!!, password!!, object : RepositoryCallback<String> {
                    override fun onSuccess(result: List<String>) {}
                    override fun onSuccess() {}
                    override fun onError(msg: String) {
                        authListener?.onFailure(msg)
                    }
                })
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }

    }

    fun onLogin(view: View) {
        Intent(view.context, AuthActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignup(view: View) {
        Intent(view.context, AuthActivity::class.java).also {
            view.context.startActivity(it)
        }
    }


    fun onSignupButtonClick(view: View) {
        authListener?.onStarted()

        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Name is required")
            return
        }

        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email is required")
            return
        }

        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Please enter a password")
            return
        }

        if (password != passwordconfirm) {
            authListener?.onFailure("Password did not match")
            return
        }


        Coroutines.main {
            try {
                repository.signUp(email!!, password!!, object : RepositoryCallback<String> {
                    override fun onSuccess(result: List<String>) {}
                    override fun onSuccess() {}
                    override fun onError(msg: String) {
                        authListener?.onFailure(msg)
                    }
                })
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }

    }

}