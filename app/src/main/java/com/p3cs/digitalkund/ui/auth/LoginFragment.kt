package com.p3cs.digitalkund.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.p3cs.digitalkund.R
import com.p3cs.digitalkund.databinding.LoginFragmentBinding
import kotlinx.android.synthetic.main.login_fragment.*


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    lateinit var binding: LoginFragmentBinding
    lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    val RC_SIGN_IN = 9001
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        activity?.let {

            viewModel = ViewModelProviders.of(it).get(AuthViewModel::class.java)
            binding.viewModel = viewModel

            val mGoogleSignInClient = GoogleSignIn.getClient(it, gso)
            btn_google.setOnClickListener {
                startActivityForResult(
                    mGoogleSignInClient.signInIntent,
                    RC_SIGN_IN
                )
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
                val account = task.getResult(ApiException::class.java)
                account?.let { viewModel.loginWithGoogle(it) }
//            } catch (e: ApiException) {
//                e.printStackTrace()
//            }

        }
    }

}
