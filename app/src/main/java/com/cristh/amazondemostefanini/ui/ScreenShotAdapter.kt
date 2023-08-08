package com.cristh.amazondemostefanini.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.cristh.amazondemostefanini.R

class ScreenShotAdapter(private val urlList: List<String>):
    RecyclerView.Adapter<ScreenShotAdapter.ScreenShotView>() {


    class ScreenShotView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val screenshot: ImageView = itemView.findViewById<ImageView>(R.id.screenshot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenShotView {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_screen_item, parent, false)

        return ScreenShotView(view)
    }

    override fun getItemCount(): Int = urlList.size

    override fun onBindViewHolder(holder: ScreenShotView, position: Int) {
        holder.screenshot.load(urlList[position]) {
            placeholder(R.drawable.splash_icon)
        }
    }
}