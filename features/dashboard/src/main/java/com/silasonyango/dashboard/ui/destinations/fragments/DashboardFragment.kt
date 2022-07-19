package com.silasonyango.dashboard.ui.destinations.fragments

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.silasonyango.common.Status
import com.silasonyango.dashboard.dagger.DashboardComponent
import com.silasonyango.dashboard.dagger.provider.DashboardComponentProvider
import com.silasonyango.dashboard.databinding.FragmentDashboardBinding
import com.silasonyango.dashboard.ui.adapter.BannerViewPagerAdapter
import com.silasonyango.dashboard.ui.adapter.ShowsRecyclerviewAdapter
import com.silasonyango.dashboard.ui.viewmodel.DashboardViewModel
import com.silasonyango.dashboard.ui.viewmodel.DashboardViewModelProvider
import com.silasonyango.tvmaze.models.ShowResponseModel
import javax.inject.Inject


class DashboardFragment : Fragment(), ShowsRecyclerviewAdapter.ShowsRecyclerviewAdapterCallback {
    private var gridLayoutManager: GridLayoutManager? = null
    private val delay = 4000
    private var page = 0
    var bannerViewPagerAdapter: BannerViewPagerAdapter? = null
    val handler: Handler = Handler(Looper.getMainLooper())
    var runnable: Runnable = object : Runnable {
        override fun run() {
            if (bannerViewPagerAdapter?.getCount() == page) {
                page = 0
            } else {
                page++
            }
            binding.bannerViewPager.setCurrentItem(page, true)
            handler.postDelayed(this, delay.toLong())
        }
    }

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
                        progressBar.isVisible = false
                        mainContent.isVisible = true
                        val bannerRadioGroup = RadioGroup(requireContext())
                        bannerRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
                        it.data?.let { data ->
                            if (!data.isEmpty()) {
                                for (i in 0..data.size) {
                                    val radioButton = RadioButton(requireContext())
                                    radioButton.id = i
                                    radioButton.setScaleX(0.5.toFloat())
                                    radioButton.setScaleY(0.5.toFloat())
                                    radioButton.setEnabled(false)

                                    if (i == 0) {
                                        radioButton.setChecked(true);
                                    }

                                    val colorStateList = ColorStateList(
                                        arrayOf(
                                            intArrayOf(-R.attr.state_enabled), intArrayOf(
                                                R.attr.state_enabled
                                            )
                                        ), intArrayOf(
                                            Color.WHITE, Color.WHITE
                                        )
                                    )


                                    radioButton.setButtonTintList(colorStateList)
                                    radioButton.invalidate()

                                    bannerRadioGroup.addView(radioButton)
                                }
                                (binding.radioGroup as ViewGroup).addView(
                                    bannerRadioGroup
                                )

                                val showsRecyclerviewAdapter = ShowsRecyclerviewAdapter(requireContext(), data, this@DashboardFragment)
                                gridLayoutManager = GridLayoutManager(requireContext(),3)
                                showsRecyclerview.apply {
                                    setHasFixedSize(true)
                                    layoutManager = gridLayoutManager
                                    adapter = showsRecyclerviewAdapter
                                }
                            }
                        }


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
                                bannerRadioGroup.check(position + 1)
                            }

                            override fun onPageScrollStateChanged(state: Int) {}
                        })

                        bannerViewPagerAdapter =
                            BannerViewPagerAdapter(requireContext(), it.data?.subList(0, 10)!!)
                        bannerViewPager.adapter = bannerViewPagerAdapter
                    }
                }
                Status.LOADING -> {
                    binding.apply {
                        progressBar.isVisible = true
                    }
                }
                Status.ERROR -> {
                    binding.apply {
                        progressBar.isVisible = false
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        handler.postDelayed(runnable, delay.toLong())
    }

    override fun onPause() {
        super.onPause()
        handler.postDelayed(runnable, delay.toLong())
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            DashboardFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onItemClicked(selectedShow: ShowResponseModel) {
        val dialog = ShowDetailsFragment.newInstance(selectedShow)
        dialog.show(childFragmentManager, ShowDetailsFragment.TAG)
    }
}