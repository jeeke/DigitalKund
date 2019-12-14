package com.p3cs.digitalkund.ui.prevPatents

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.p3cs.digitalkund.R


class LaunchedFragment : Fragment() {

    companion object {
        fun newInstance() = LaunchedFragment()
    }

    private lateinit var viewModel: LaunchedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.launched_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LaunchedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
