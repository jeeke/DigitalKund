package co.p3cs.digitalkund.ui.auth

import android.app.Application
import android.content.Intent
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.findNavController
import co.p3cs.digitalkund.R
import co.p3cs.digitalkund.data.repos.AuthRepository
import co.p3cs.digitalkund.data.repos.AuthRepository.Companion.currentUser
import co.p3cs.digitalkund.data.repos.baseRepo.RepositoryCallback
import co.p3cs.digitalkund.ui.MainActivity
import co.p3cs.digitalkund.util.Coroutines
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class AuthViewModel(
    application: Application,
    private val repository: AuthRepository
) : AndroidViewModel(application) {

    var email: String? = null
    var password: String? = null
    var authListener: AuthListener? = null
    var selectedTabPosition: Int = 0

    fun navigateToSignUp(v: View) {
        v.findNavController().navigate(R.id.signupFragment)
    }


    fun checkSignedIn() {
        val context = getApplication<Application>().applicationContext
        currentUser()?.let {
            if (it.isEmailVerified)
                Intent(context, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(it)
                }
        }
    }

    private fun callbackFactory(msgDone: String): RepositoryCallback<String> {
        return object : RepositoryCallback<String> {
            override fun onSuccess(result: List<String>) {
                authListener?.onSuccess(msgDone)
            }

            override fun onSuccess() {
                authListener?.onSuccess(msgDone)
            }

            override fun onError(msg: String) {
                authListener?.onFailure(msg)
            }

        }
    }

    fun onContinueBtnClick(view: View) {
        if (selectedTabPosition == 1) onSignup(view)
        else onLogin()
    }

    fun onLogin() {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }
        Coroutines.io { repository.login(email!!, password!!, callbackFactory("")) }

    }

    fun onSignup(v: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }
        Coroutines.io {
            repository.signUp(
                email!!,
                password!!,
                callbackFactory("Verification Mail Sent")
            )
        }

    }

    fun loginWithGoogle(acct: GoogleSignInAccount) {
        repository.continueWithGoogle(acct, callbackFactory(""))
    }

}