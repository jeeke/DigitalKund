package co.p3cs.digitalkund.ui.prevPatents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import co.p3cs.digitalkund.R

class DraftsFragment : Fragment() {

    companion object {
        fun newInstance() = DraftsFragment()
    }

    private lateinit var viewModel: DraftsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.drafts_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DraftsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
