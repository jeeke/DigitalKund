package com.p3cs.digitalkund.ui.auth

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.p3cs.digitalkund.R

class UserSelectionFragment : Fragment() {

    companion object {
        fun newInstance() = UserSelectionFragment()
    }

    private lateinit var viewModel: UserSelectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_selection_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UserSelectionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
