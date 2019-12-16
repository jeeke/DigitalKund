package com.p3cs.digitalkund.data.repos

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.*
import com.p3cs.digitalkund.data.repos.baseRepo.RepositoryCallback

class AuthRepository {

    fun currentUser() = FirebaseAuth.getInstance().currentUser

    fun login(email: String, password: String, callback: RepositoryCallback<String>) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                val e = task.exception
                when {
                    task.isSuccessful -> callback.onSuccess()
                    e is FirebaseAuthInvalidCredentialsException -> callback.onError("Invalid Email or Password")
                    e is FirebaseAuthInvalidUserException -> callback.onError("Email not registered!, SIGN UP")
                    else -> callback.onError("Login Error!")
                }

            }
    }

    private fun continueWithGoogle(
        acct: GoogleSignInAccount,
        callback: RepositoryCallback<String>
    ) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { t ->
                Log.e("Google Sign in Error",t.exception.toString())
                if (t.isSuccessful) callback.onSuccess()
                else callback.onError("Something went wrong!")
            }
    }

    fun signUp(email: String, password: String, callback: RepositoryCallback<String>) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> sendEmailVerificationLink(callback)
                    task.exception is FirebaseAuthUserCollisionException -> callback.onError("Email already in use!, SIGN IN")
                    else -> callback.onError("SignUp Unsuccessful")
                }
            }
    }

    private fun sendEmailVerificationLink(callback: RepositoryCallback<String>) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) callback.onSuccess()
            else callback.onError("Verification Failed")
        }
    }

    fun sendPasswordResetMail(emailAddress: String, callback: RepositoryCallback<String>) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                val e = task.exception
                when {
                    task.isSuccessful -> callback.onSuccess()
                    e is FirebaseAuthInvalidCredentialsException -> callback.onError("Invalid Email")
                    e is FirebaseAuthInvalidUserException -> callback.onError("Email Not Registered")
                    else -> callback.onError("Some error occurred")
                }
            }
    }

    fun editPassword(password: String, newPassword: String, callback: RepositoryCallback<String>) {
        FirebaseAuth.getInstance().currentUser?.let {
            val credential = EmailAuthProvider.getCredential(it.email!!, password)
            it.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) callback.onError("Wrong Password!")
                    else it.updatePassword(newPassword)
                        .addOnCompleteListener { task1 ->
                            if (task1.isSuccessful) callback.onSuccess()
                            else callback.onError("Password Updating Unsuccessful")
                        }
                }
            return
        }
        callback.onError("Password Updating Unsuccessful")
    }

//    fun logout(context: Context) {
//        FirebaseAuth.getInstance().signOut()
//        // Google sign out
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(context.getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        val mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
//        mGoogleSignInClient.signOut()
//        //redirect to login screen
//        Intent(context, MainActivity::class.java).also {
//            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            context.startActivity(it)
//        }
//    }

}