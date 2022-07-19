package com.silasonyango.dashboard.ui.destinations.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.silasonyango.common.Status
import com.silasonyango.dashboard.dagger.DashboardComponent
import com.silasonyango.dashboard.dagger.provider.DashboardComponentProvider
import com.silasonyango.dashboard.databinding.FragmentDashboardBinding
import com.silasonyango.dashboard.ui.adapter.BannerViewPagerAdapter
import com.silasonyango.dashboard.ui.viewmodel.DashboardViewModel
import com.silasonyango.dashboard.ui.viewmodel.DashboardViewModelProvider
import javax.inject.Inject


class DashboardFragment : Fragment() {
    private val delay = 4000
    private var page = 0
//    var runnable: Runnable = object : Runnable {
//        override fun run() {
//            if (crossSellViewPagerAdapter.getCount() === page) {
//                page = 0
//            } else {
//                page++
//            }
//            crossSellViewPager.setCurrentItem(page, true)
//            handler.postDelayed(this, delay)
//        }
//    }

    private lateinit var binding: FragmentDashboardBinding

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
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val rootView = binding.rootView

        return rootView
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
                    binding.apply {
                        bannerViewPager.addOnPageChangeListener(object : OnPageChangeListener {
                            override fun onPageScrolled(
                                position: Int,
                                positionOffset: Float,
                                positionOffsetPixels: Int
                            ) {
                            }

                            @SuppressLint("ResourceType")
                            override fun onPageSelected(position: Int) {
                                page = position
                            }

                            override fun onPageScrollStateChanged(state: Int) {}
                        })

                        val bannerViewPagerAdapter = BannerViewPagerAdapter(requireContext(),it.data?.subList(0,10)!!)
                        bannerViewPager.adapter = bannerViewPagerAdapter
                    }
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

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
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