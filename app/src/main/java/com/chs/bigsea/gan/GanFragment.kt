package com.chs.bigsea.gan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.chs.bigsea.R


class GanFragment : Fragment() {

    companion object {
        fun newInstance() = GanFragment()
    }

    private lateinit var viewModel: GanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.gan_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GanViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
