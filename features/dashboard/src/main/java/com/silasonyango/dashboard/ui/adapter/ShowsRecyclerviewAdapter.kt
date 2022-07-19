package com.silasonyango.dashboard.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.silasonyango.dashboard.R
import com.silasonyango.tvmaze.models.ShowResponseModel

class ShowsRecyclerviewAdapter(
    private val context: Context,
    private val showsList: List<ShowResponseModel>,
    val showsRecyclerviewAdapterCallback: ShowsRecyclerviewAdapterCallback
) : RecyclerView.Adapter<ShowsRecyclerviewAdapter.ViewHolder>() {

    interface ShowsRecyclerviewAdapterCallback {

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var networkImageView: NetworkImageView

        init {
            networkImageView = view.findViewById(R.id.showImage)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.show_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentShow = showsList.get(position)
        val imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader()
        imageLoader.get(currentShow.image.original, ImageLoader.getImageListener(viewHolder.networkImageView,android.R.drawable.ic_dialog_alert, android.R.drawable.ic_dialog_alert))
        viewHolder.networkImageView.setImageUrl(currentShow.image.original,imageLoader)
        viewHolder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount() = showsList.size
}