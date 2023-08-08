package com.cristh.amazondemostefanini.ui

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import coil.load
import com.cristh.amazondemostefanini.R
import com.cristh.amazondemostefanini.databinding.ViewholderAppBinding
import com.cristh.amazondemostefanini.model.AppItem
import java.util.Locale

class AppItemGVAdapter(
    context: Context,
    var itemList: List<AppItem>?
): ArrayAdapter<AppItemViewHolder>(context, 0) {
    private lateinit var itemBinding: ViewholderAppBinding
    private val inflater: LayoutInflater = LayoutInflater.from(this.context)

    override fun getCount(): Int = itemList?.size ?: 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var gridItemView = convertView
        val viewHolder: AppItemViewHolder

        if ( gridItemView == null ) {
            itemBinding = ViewholderAppBinding.inflate(inflater)
            gridItemView = itemBinding.root
            viewHolder = AppItemViewHolder()
            viewHolder.icon = itemBinding.AppIcon
            viewHolder.name = itemBinding.AppName
            viewHolder.dev = itemBinding.AppDev
            viewHolder.rating = itemBinding.AppRating
            viewHolder.price = itemBinding.AppPrice
            gridItemView.tag = viewHolder
        } else {
            viewHolder = gridItemView.tag as AppItemViewHolder
        }

        val appItem: AppItem? = itemList?.get(position)

        gridItemView.let { view ->
            appItem?.let { item ->
                view.findViewById<ImageView>(R.id.AppIcon).load( item.icon ){
                    placeholder(R.drawable.splash_icon)
                }
                view.findViewById<TextView>(R.id.AppName).text = item.name
                view.findViewById<TextView>(R.id.AppDev).text = item.developer
                // TODO -> get currency
                view.findViewById<TextView>(R.id.AppPrice).text = if (item.price > 0.5f) {
                    String.format(
                        locale = Locale.getDefault(),
                        format = context.getString(R.string.money_format),
                        "%.2f".format(item.price)
                    )
                } else {
                    context.getString(R.string.free_text)
                }

                view.findViewById<RatingBar>(R.id.AppRating).apply {
                    numStars = 5
                    stepSize = 0.1f
                    var stars: LayerDrawable = this.progressDrawable as LayerDrawable
                    rating = item.rating.toFloat()

                    if (item.rating >= 3.0f) {
                        stars.setTint(android.graphics.Color.GREEN)
                    } else {
                        stars.setTint(android.graphics.Color.RED)
                    }
                }
            }

        }
        return gridItemView
    }
}

class AppItemViewHolder {
    var icon: ImageView? = null
    var name: TextView? = null
    var dev: TextView? = null
    var rating: RatingBar? = null
    var price: TextView? = null
}