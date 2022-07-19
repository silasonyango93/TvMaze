package com.silasonyango.dashboard.ui.destinations.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.silasonyango.common.Status
import com.silasonyango.dashboard.R
import com.silasonyango.dashboard.dagger.DashboardComponent
import com.silasonyango.dashboard.dagger.provider.DashboardComponentProvider
import com.silasonyango.dashboard.viewmodel.DashboardViewModel
import com.silasonyango.dashboard.viewmodel.DashboardViewModelProvider
import javax.inject.Inject


class DashboardFragment : Fragment() {
    lateinit var dashboardComponent: DashboardComponent

    @Inject
    lateinit var dashboardViewModel: DashboardViewModel

    @Inject
    lateinit var dashboardViewModelProvider: DashboardViewModelProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.dashboardComponent =
            (context.applicationContext as DashboardComponentProvider).provideDashboardComponent()
        this.dashboardComponent.inject(this)
        dashboardViewModel = dashboardViewModelProvider.provideDashboardViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObservers()
        dashboardViewModel.fetchShowsByPage(0)
    }


    private fun registerObservers() {
        dashboardViewModel.showsResponse.observe(viewLifecycleOwner) {
            when (it?.status) {
                Status.SUCCESS -> {
                    System.out.println()
                }
                Status.LOADING -> {
                    System.out.println()
                }
                Status.ERROR -> {
                    System.out.println()
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}