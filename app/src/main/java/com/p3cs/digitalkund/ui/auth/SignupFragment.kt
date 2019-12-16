package com.p3cs.digitalkund.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.p3cs.digitalkund.databinding.SignupFragmentBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SignupFragment : Fragment(),KodeinAware {


    companion object {
        fun newInstance() = SignupFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = SignupFragmentBinding.inflate(inflater, container, false)
//        set variables in Binding
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewModel = viewModel
//        viewModel.authListener = this
        return binding.root
    }


}
