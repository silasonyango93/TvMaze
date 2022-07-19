package com.silasonyango.dashboard.ui.destinations.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.android.volley.toolbox.ImageLoader
import com.silasonyango.dashboard.R
import com.silasonyango.dashboard.databinding.FragmentDashboardBinding
import com.silasonyango.dashboard.databinding.FragmentShowDetailsBinding
import com.silasonyango.dashboard.ui.adapter.CustomVolleyRequest
import com.silasonyango.tvmaze.models.ShowResponseModel

private const val SELECTED_SHOW = "selectedShow"


class ShowDetailsFragment : DialogFragment() {
    private lateinit var binding: FragmentShowDetailsBinding
    private var selectedShow: ShowResponseModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedShow = it.getParcelable<ShowResponseModel>(SELECTED_SHOW)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowDetailsBinding.inflate(inflater, container, false)
        val rootView = binding.rootView
        binding.apply {
            tvGenre.text = customConcatGenre(selectedShow?.genres)
                ?: getString(R.string.genre_not_available_label)
            tvDescription.text =
                selectedShow?.summary ?: getString(R.string.summary_not_available_label)
            val imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader()
            imageLoader.get(selectedShow?.image?.original, ImageLoader.getImageListener(detailsImage,android.R.drawable.ic_dialog_alert, android.R.drawable.ic_dialog_alert))

            detailsImage.setImageUrl(selectedShow?.image?.original,imageLoader)
        }
        return rootView
    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            val matchParent = ViewGroup.LayoutParams.MATCH_PARENT
            window?.setLayout(matchParent, matchParent)
            window?.setBackgroundDrawable(null)
        }
    }

    fun customConcatGenre(genreList: List<String>?): String? {
        if (genreList == null || genreList.isEmpty()) {
            return null
        }
        var genreString: String? = ""
        for (currentGenre in genreList) {
            genreString = genreString + currentGenre + ", "
        }
        return genreString
    }

    companion object {
        val TAG = "showDetailsFragment"

        @JvmStatic
        fun newInstance(selectedShow: ShowResponseModel) =
            ShowDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(SELECTED_SHOW, selectedShow)
                }
            }
    }
}