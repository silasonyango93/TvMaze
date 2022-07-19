package com.silasonyango.dashboard.ui.adapter

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
        imageLoader.get(currentShow.image.original, ImageLoader.getImageListener(ivBannerImage,android.R.drawable.ic_dialog_alert, android.R.drawable.ic_dialog_alert))

        ivBannerImage.setImageUrl(currentShow.image.original,imageLoader)
        container.addView(view)
        return view
    }

    fun calculateInSampleSize(
        options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int
    ): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize > reqHeight
                && halfWidth / inSampleSize > reqWidth
            ) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    fun decodeSampledBitmapFromResource(
        res: Resources?, resId: Int,
        reqWidth: Int, reqHeight: Int
    ): Bitmap? {

        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }
}