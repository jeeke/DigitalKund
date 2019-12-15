package com.p3cs.digitalkund.data.repos

class AuthRepositories {
    //login
    //signup
    //reset password
    //forgot password
    //logout
    //signup with google
    //signin with google
    //signup with facebook
    //signin with facebook

    fun login(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener({ task ->
                val e = task.getException()
                if (e == null)
                    notifyListener(
                        task.isSuccessful(),
                        SERVER_LOGIN,
                        "Login Successful",
                        "Login Unsuccessful"
                    ) { login(email, password) }
                else if (e is FirebaseAuthInvalidCredentialsException) {
                    notifyListener(
                        false,
                        SERVER_LOGIN,
                        "",
                        "Invalid Email or Password", null
                    )
                } else if (e is FirebaseAuthInvalidUserException) {
                    notifyListener(
                        false,
                        SERVER_LOGIN,
                        "",
                        "Email not registered!, SIGN UP", null
                    )
                }

            })
    }

}