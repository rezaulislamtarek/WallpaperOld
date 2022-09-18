package com.diatomicsoft.wallpaperapp.ui.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.diatomicsoft.wallpaperapp.adapter.WallpaperAdapter
import com.diatomicsoft.wallpaperapp.databinding.FragmentImagesBinding
import com.diatomicsoft.wallpaperapp.model.data.Wallpaper
import com.diatomicsoft.wallpaperapp.util.WallpaperItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImagesFragment : Fragment(), WallpaperItemClickListener {

    lateinit var binding: FragmentImagesBinding
    private val viewModel : WallpaperViewModel by viewModels()
    val args: ImagesFragmentArgs by navArgs()
    lateinit var listener: WallpaperItemClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentImagesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        viewModel.getWallPapers(args.category.id)
        binding.toolBarTitle.text = args.category.name

        binding.backPress.setOnClickListener { requireActivity().onBackPressed() }


        listener = this


        viewModel.wallpaperList.observe(viewLifecycleOwner, Observer {

            val genericAdapter = WallpaperAdapter(it,listener)
            binding.viewpager.apply {
                this.adapter = genericAdapter
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
        lifecycleScope.launch{
            viewModel.addToFav(wallpaper)
        }
    }

    override fun onDownLoadClick(wallpaper: Wallpaper) {
        viewModel.downloadWallpaper(wallpaper)
        Toast.makeText(requireContext(),wallpaper.walpaper_url+" Downloading...",Toast.LENGTH_SHORT).show()
    }

    override fun setWallPaperClick(wallpaper: Wallpaper) {
        Toast.makeText(requireContext(),wallpaper.walpaper_url,Toast.LENGTH_SHORT).show()
        viewModel.useAsWallpaper(wallpaper)

    }
}