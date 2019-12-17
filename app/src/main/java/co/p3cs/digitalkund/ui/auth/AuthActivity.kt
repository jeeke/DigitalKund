package co.p3cs.digitalkund.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import co.p3cs.digitalkund.R
import co.p3cs.digitalkund.util.hide
import co.p3cs.digitalkund.util.show
import co.p3cs.digitalkund.util.snackbar
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.login_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class AuthActivity : AppCompatActivity(), AuthListener, KodeinAware {
    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        viewModel.authListener = this
        viewModel.checkSignedIn()
    }


    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(message: String) {
        if (message == "") rootView.snackbar(message)
        progress_bar.hide()
        viewModel.checkSignedIn()
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        rootView.snackbar(message)
    }

}
