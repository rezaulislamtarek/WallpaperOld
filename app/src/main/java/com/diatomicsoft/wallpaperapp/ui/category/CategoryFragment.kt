package com.diatomicsoft.wallpaperapp.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.diatomicsoft.wallpaperapp.R
import com.diatomicsoft.wallpaperapp.adapter.generic_adapter.GenericAdapter
import com.diatomicsoft.wallpaperapp.databinding.FragmentCategoryBinding
import com.diatomicsoft.wallpaperapp.model.data.Category
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    private val viewModel: CategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentCategoryBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        val genericAdapter = GenericAdapter<Category>(R.layout.rv_category_item)

        binding.navDrawer.setOnClickListener {
            findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToNavDrawerFragment())
        }


        viewModel.categories.observe(viewLifecycleOwner, Observer { categoryData ->
            binding.apply {
                genericAdapter.addItems(categoryData)
                rvCategory.layoutManager = GridLayoutManager(requireContext(), 1)
                //rvCategory.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
                rvCategory.adapter = genericAdapter
            }
            genericAdapter.setOnListItemViewClickListener( object : GenericAdapter.OnListItemViewClickListener{
                override fun onClick(view: View, position: Int) {
                    findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToImagesFragment(categoryData.get(position)))
                }
            })

        })
    }
}