package com.p3cs.digitalkund.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.p3cs.digitalkund.R
import java.util.*

class SignupFragment : Fragment() {

    companion object {
        fun newInstance() = SignupFragment()
    }

    private lateinit var viewModel: SignupDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val r : Int = (Date().time % 3).toInt()
        val res = arrayOf(R.layout.signup_detail_innovator,R.layout.signup_detail_customer,R.layout.signup_detail_producer);
        return inflater.inflate(res[r], container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SignupDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
