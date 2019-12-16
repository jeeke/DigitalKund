package com.p3cs.digitalkund.ui.auth

import android.app.Application
import android.content.Intent
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.p3cs.digitalkund.R
import com.p3cs.digitalkund.data.repos.AuthRepository
import com.p3cs.digitalkund.data.repos.baseRepo.RepositoryCallback
import com.p3cs.digitalkund.ui.MainActivity
import com.p3cs.digitalkund.util.ApiException
import com.p3cs.digitalkund.util.Coroutines
import com.p3cs.digitalkund.util.NoInternetException


class AuthViewModel(
    application: Application,
    private val repository: AuthRepository
) : AndroidViewModel(application) {

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordconfirm: String? = null

    var authListener: AuthListener? = null

    fun currentUser() = repository.currentUser()

    fun navigateToSignUp(v: View) {
        v.findNavController().navigate(R.id.signupFragment)
    }

    fun checkSignedIn() {
        val context = getApplication<Application>().applicationContext
            currentUser()?.let {
                Intent(context, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(it)
                }
            }
    }

    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }
        Coroutines.main {
            try {
                repository.login(email!!, password!!, object : RepositoryCallback<String> {
                    override fun onSuccess(result: List<String>) {
                        authListener?.onSuccess()
                        checkSignedIn()
                    }

                    override fun onSuccess() {
                        authListener?.onSuccess()
                        checkSignedIn()
                    }

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

    val RC_SIGN_IN = 9001
    fun loginWithGoogle(acct: GoogleSignInAccount) {
        repository.continueWithGoogle(acct, object : RepositoryCallback<String> {
            override fun onSuccess() {
                authListener?.onSuccess()
                checkSignedIn()
            }

            override fun onError(msg: String) {
                authListener?.onFailure(msg)
            }

            override fun onSuccess(result: List<String>) {
                authListener?.onSuccess()
                checkSignedIn()
            }

        })
    }

    fun onLogin(view: View) {
        val context = getApplication<Application>().applicationContext
        Intent(context, AuthActivity::class.java).also {
            context.startActivity(it)
        }
    }

    fun onSignup(view: View) {
        val context = getApplication<Application>().applicationContext
        Intent(context, AuthActivity::class.java).also {
            context.startActivity(it)
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