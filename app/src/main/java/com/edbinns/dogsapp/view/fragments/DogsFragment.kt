package com.edbinns.dogsapp.view.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.edbinns.dogsapp.R
import com.edbinns.dogsapp.databinding.FragmentDogsBinding
import com.edbinns.dogsapp.models.Dog
import com.edbinns.dogsapp.view.activitys.ItemDogActivity
import com.edbinns.dogsapp.view.adapters.DogsAdapter
import com.edbinns.dogsapp.view.adapters.ItemClickListener
import com.edbinns.dogsapp.viewmodel.DogsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DogsFragment : Fragment(), ItemClickListener<Dog> {

    private var _binding: FragmentDogsBinding? = null
    private val binding get() = _binding!!
    private val dogsViewModel: DogsViewModel by viewModels()

    private var loading = false
    private var search = false
    private var breed = ""
    private val dogsAdapter: DogsAdapter by lazy {
        DogsAdapter(this)
    }
    private val manager: StaggeredGridLayoutManager by lazy {
        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDogsBinding.inflate(inflater, container, false)

        with(binding.rvDogs) {
            layoutManager = manager
            adapter = dogsAdapter
        }
        binding.swipeContainerDogs.setOnRefreshListener {
            dogsAdapter.deleteData()
            dogsViewModel.getDogsImages()
            search = false
        }

        binding.btnSearch.setOnClickListener {
            val dialog = SearchDialog()
            fragmentManager?.let { manager -> dialog.show(manager, "SearchDialog") }

        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoader()
        dogsViewModel.getDogsImages()
        scrollPaging()
        observe()
    }


    private fun observe() {
        dogsViewModel.imagesList.observe(viewLifecycleOwner, Observer { list ->
            dataChange(list)
        })

        dogsViewModel.searchingList.observe(viewLifecycleOwner, Observer { list ->
            dogsAdapter.deleteData()
            breed = list[0].breed
            search = true
            dataChange(list)
        })
    }

    private fun dataChange(list: List<Dog>) {
        hideLoader()
        if (list.isNullOrEmpty())
            showNotFoundLayout()
        else {
            hideNotFoundLayout()
            dogsAdapter.updateData(list)
        }

    }

    private fun scrollPaging() {

        binding.rvDogs.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("NewApi")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val result = dogsViewModel.isScrolling(manager, dogsAdapter, loading)
                    if (result) {
                        showLoader()
                        if (search) {
                            dogsViewModel.getDogsByBreed(breed)
                        } else {
                            dogsViewModel.getDogsImages()
                        }

                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun showNotFoundLayout() {
        binding.layoutNotFound.notFoundLayout.visibility = View.VISIBLE
    }

    private fun hideNotFoundLayout() {
        binding.layoutNotFound.notFoundLayout.visibility = View.GONE
    }

    private fun showLoader() {
        binding.swipeContainerDogs.isRefreshing = true
        loading = true

    }

    private fun hideLoader() {
        binding.swipeContainerDogs.isRefreshing = false
        loading = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCLickListener(data: Dog) {
        println("data $data")
        val bundle = bundleOf("info" to data)
        println(bundle)
        val intent = Intent(activity, ItemDogActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)

    }


}