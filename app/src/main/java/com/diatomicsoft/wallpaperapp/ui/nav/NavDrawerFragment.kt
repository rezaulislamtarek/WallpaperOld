package com.diatomicsoft.wallpaperapp.ui.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diatomicsoft.wallpaperapp.databinding.FragmentNavDrawerBinding

class NavDrawerFragment : Fragment() {
    lateinit var binding: FragmentNavDrawerBinding
//    private val viewModel: NavViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNavDrawerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.apply {
            this.btnFab.setOnClickListener {
                findNavController().navigate(NavDrawerFragmentDirections.actionNavDrawerFragmentToFavFragment())
            }
            this.btnClose.setOnClickListener {
                activity?.onBackPressed()
            }
            this.btnDownload.setOnClickListener {
                findNavController().navigate(NavDrawerFragmentDirections.actionNavDrawerFragmentToDownloadFragment())
            }
        }
    }

}