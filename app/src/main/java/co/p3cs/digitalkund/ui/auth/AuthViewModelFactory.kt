package co.p3cs.digitalkund.ui.auth

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.p3cs.digitalkund.data.repos.AuthRepository


@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(
    private val application: Application,
    private val repository: AuthRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(application,repository) as T
    }
}