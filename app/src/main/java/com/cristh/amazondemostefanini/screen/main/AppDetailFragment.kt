package com.cristh.amazondemostefanini.screen.main

import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.cristh.amazondemostefanini.R
import com.cristh.amazondemostefanini.databinding.FragmentAppDetailBinding
import com.cristh.amazondemostefanini.ui.CommentsAdapter
import com.cristh.amazondemostefanini.ui.ScreenShotAdapter
import com.cristh.amazondemostefanini.util.DEV
import com.cristh.amazondemostefanini.util.ICON
import com.cristh.amazondemostefanini.util.ID
import com.cristh.amazondemostefanini.util.NAME
import com.cristh.amazondemostefanini.util.PRICE
import com.cristh.amazondemostefanini.util.RATING
import com.cristh.amazondemostefanini.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class AppDetailFragment : Fragment() {

    var binding: FragmentAppDetailBinding? = null

    private val detailViewModel: DetailViewModel by viewModels()

    private lateinit var horizontalAdapter: ScreenShotAdapter
    private lateinit var commentAdapter: CommentsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel.getDetailsFromJson(R.raw.app_response1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        horizontalAdapter = ScreenShotAdapter(detailViewModel.details?.screenshots ?: listOf())
        detailViewModel.details?.let {
            commentAdapter = CommentsAdapter(it.comments)
        }

        binding?.let {
            activity?.apply {
                it.screenshotRecycler.layoutManager = LinearLayoutManager(
                    this,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                it.screenshotRecycler.adapter = horizontalAdapter

                it.commentSection.layoutManager = LinearLayoutManager(
                    this,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                it.commentSection.adapter = commentAdapter
            }
            it.detailIcon.load(this.arguments?.getString(ICON, "")) {
                placeholder(R.drawable.splash_icon)
            }
            it.detailName.text = this.arguments?.getString(NAME, "App")
            it.detailDev.text= this.arguments?.getString(DEV, "Dev")

            val price: Float? = this.arguments?.getFloat(PRICE, 0.0f)
            if (price != null) {
                it.detailPrice.text = if (price > 0.5f) {
                    String.format(
                        locale = Locale.getDefault(),
                        format = requireContext().getString(R.string.money_format),
                        "%.2f".format(price)
                    )
                } else {
                    context?.getString(R.string.free_text)
                }
            }

            val rating = this.arguments?.getFloat(RATING, 0.0f)
            it.detailRating.apply {
                numStars = 5
                stepSize = 0.1f
                var stars: LayerDrawable = this.progressDrawable as LayerDrawable
                if (rating != null) {
                    this.rating = rating.toFloat()

                    if (rating >= 3.0f) {
                        stars.setTint(android.graphics.Color.GREEN)
                    } else {
                        stars.setTint(android.graphics.Color.RED)
                    }
                }
            }
            it.detailNumRating.text = rating.toString()

            it.closeDetailBtn.setOnClickListener {
                activity?.onBackPressed()
            }

            detailViewModel.details?.let { details ->
                it.detailDesc.text = details.description_text
                if (details.installed) {
                    it.installAppBtn.text = getString(R.string.installed_button_text)
                    it.installAppBtn.background = activity?.getDrawable(R.drawable.rounded_gray_background)
                } else {
                    it.installAppBtn.text = getString(R.string.install_button_text)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(
            id: String,
            name: String,
            price: Float,
            rating: Float,
            icon: String,
            dev: String
        ) =
            AppDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ID, id)
                    putString(NAME, name)
                    putFloat(PRICE, price)
                    putFloat(RATING, rating)
                    putString(ICON, icon)
                    putString(DEV, dev)
                }
            }
    }
}