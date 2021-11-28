package com.edbinns.dogsapp.view.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.edbinns.dogsapp.R
import com.edbinns.dogsapp.databinding.DialogDogsBinding
import com.edbinns.dogsapp.models.Dog
import com.edbinns.dogsapp.utils.splitBreed
import com.edbinns.dogsapp.view.adapters.DogsAdapter
import com.edbinns.dogsapp.view.adapters.ItemClickListener
import com.edbinns.dogsapp.viewmodel.DogsViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogsDialog : DialogFragment(), ItemClickListener<Dog> {

    private var _binding: DialogDogsBinding? = null
    private val binding get() = _binding!!

    private var loading= false
    private lateinit var dog : Dog
    private val dogsAdapter: DogsAdapter by lazy {
        DogsAdapter(this)
    }
    private val manager : StaggeredGridLayoutManager by lazy {
        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }
    private val dogsViewModel: DogsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogDogsBinding.inflate(inflater, container, false)

        dog =  arguments?.getSerializable("info") as Dog
        setInfo(dog)

        with(binding.rvDogs) {
            layoutManager = manager
            adapter = dogsAdapter
           setHasFixedSize(false);
            isNestedScrollingEnabled = false;
        }
        binding.swipeContainerDogs.setOnRefreshListener {
            dogsAdapter.deleteData()
            dogsViewModel.getDogsByBreed(dog.breed)
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoader()
        scrollPaging()
        observe()
    }

    private   fun scrollPaging() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.scroll.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

                val sam = binding.scroll.getChildAt(binding.scroll.childCount - 1)
                val diff = sam.bottom - (binding.scroll.height + scrollY)

                if ((diff == 0) && (!loading)) {
                    showLoader()
                }
            }
            }

    }

    private fun observe() {
        dogsViewModel.imagesList.observe(viewLifecycleOwner, Observer { list ->
            if (list.isNullOrEmpty()){
                showNotFoundLayout()
//                EMPTY_LIST.showMessage(requireView(), R.color.warning_color)
            }else
                hideNotFoundLayout()
            dogsAdapter.updateData(list)
            hideLoader()
        })
    }


    private fun setInfo(dog: Dog) {
        val picasso = Picasso.get()
        picasso.load(dog.imageURL)
            .into(binding.ivDogPhotoItem)
        binding.tvDogBreed.text = dog.splitBreed()
    }

    private fun showLoader(){
        binding.swipeContainerDogs.isRefreshing = true
        loading = true
        dogsViewModel.getDogsByBreed(dog.breed)
    }

    private  fun hideLoader(){
        binding.swipeContainerDogs.isRefreshing = false
        loading = false
    }
    private fun showNotFoundLayout() {
        binding.layoutNotFound.notFoundLayout.visibility = View.VISIBLE
    }
    private  fun hideNotFoundLayout(){
        binding.layoutNotFound.notFoundLayout.visibility = View.GONE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCLickListener(data: Dog) {
        val bundle = bundleOf("info" to data)
        findNavController().navigate(R.id.navDialogDogs, bundle)
    }
}