package com.silasonyango.dashboard.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.silasonyango.dashboard.R
import com.silasonyango.tvmaze.models.ShowResponseModel


class BannerViewPagerAdapter(
    private val context: Context,
    private val showsList: List<ShowResponseModel>
) : PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null


    init {
        layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
    }

    override fun getCount(): Int {
        return showsList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        //val fontAwesome = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf")
        val view: View = layoutInflater!!.inflate(R.layout.banner_template, container, false)
        val currentShow = showsList.get(position)
        val ivBannerImage: NetworkImageView = view.findViewById<View>(R.id.banner_image) as NetworkImageView
        val imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader()
        imageLoader.get(currentShow.image?.original, ImageLoader.getImageListener(ivBannerImage,android.R.drawable.ic_dialog_alert, android.R.drawable.ic_dialog_alert))

        ivBannerImage.setImageUrl(currentShow.image?.original,imageLoader)
        container.addView(view)
        return view
    }
}