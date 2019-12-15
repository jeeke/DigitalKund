package com.p3cs.digitalkund.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.p3cs.digitalkund.data.db.entities.User
import com.p3cs.digitalkund.databinding.LoginFragmentBinding
import com.p3cs.digitalkund.ui.MainActivity
import com.p3cs.digitalkund.util.hide
import com.p3cs.digitalkund.util.show
import com.p3cs.digitalkund.util.snackbar
import kotlinx.android.synthetic.main.login_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class LoginFragment : Fragment() , AuthListener, KodeinAware {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = LoginFragmentBinding.inflate(inflater, container, false)
//        set variables in Binding
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(activity, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root.snackbar(message)
    }

}
