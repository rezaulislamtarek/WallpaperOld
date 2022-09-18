package com.diatomicsoft.wallpaperapp.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.diatomicsoft.wallpaperapp.R
import com.diatomicsoft.wallpaperapp.adapter.WallpaperAdapter
import com.diatomicsoft.wallpaperapp.databinding.FragmentFavBinding
import com.diatomicsoft.wallpaperapp.model.data.Wallpaper
import com.diatomicsoft.wallpaperapp.util.WallpaperItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavFragment : Fragment(), WallpaperItemClickListener {
    lateinit var binding: FragmentFavBinding
    private val viewModel: FavViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        val listener = this

        binding.backPress.setOnClickListener {
            requireActivity().onBackPressed()
        }

        viewModel.favData.observe(viewLifecycleOwner, Observer {
            val adapter = WallpaperAdapter(it,listener)
            binding.viewpager.apply {
                this.adapter = adapter
                this.clipToPadding = false
                this.clipChildren = false
                this.offscreenPageLimit = 1
                this.getChildAt(0)?.overScrollMode =
                    RecyclerView.OVER_SCROLL_NEVER
                val compositePageTransformer = CompositePageTransformer()
                compositePageTransformer.addTransformer(MarginPageTransformer(0))
                compositePageTransformer.addTransformer { page, position ->
                    val r: Float = 1 - Math.abs(position)
                    page.scaleY = 0.85f + r * 0.15f
                    //page.scaleX = 0.85f + r * 0.15f
                }
                this.setPageTransformer(compositePageTransformer)
            }

        })
    }

    override fun addFavClick(wallpaper: Wallpaper) {
        TODO("Not yet implemented")
    }

    override fun onDownLoadClick(wallpaper: Wallpaper) {
        TODO("Not yet implemented")
    }

    override fun setWallPaperClick(wallpaper: Wallpaper) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        findNavController().popBackStack(R.id.categoryFragment,false)
    }

}