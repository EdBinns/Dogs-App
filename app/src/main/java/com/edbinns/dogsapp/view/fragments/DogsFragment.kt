package com.edbinns.dogsapp.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.edbinns.dogsapp.R
import com.edbinns.dogsapp.databinding.FragmentDogsBinding
import com.edbinns.dogsapp.models.Dog
import com.edbinns.dogsapp.view.adapters.DogsAdapter
import com.edbinns.dogsapp.view.adapters.ItemClickListener
import com.edbinns.dogsapp.viewmodel.DogsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DogsFragment : Fragment(), ItemClickListener<Dog> {

    private var _binding: FragmentDogsBinding? = null
    private val binding get() = _binding!!
    private val dogsViewModel: DogsViewModel by viewModels()

    private var loading = false
    private var filter = ""
    private val dogsAdapter: DogsAdapter by lazy {
        DogsAdapter(this)
    }
    private val manager : StaggeredGridLayoutManager by lazy {
        StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
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
            filter = ""
            dogsViewModel.getDogsImages()
        }

        searchBreed()
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
            if (list.isNullOrEmpty()){
                showNotFoundLayout()
//                EMPTY_LIST.showMessage(requireView(), R.color.warning_color)
            }else
                hideNotFoundLayout()
            dogsAdapter.updateData(list)
            hideLoader()
        })
    }
    private fun scrollPaging() {

        binding.rvDogs.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("NewApi")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy > 0) {
                    val result  = dogsViewModel.isScrolling(manager,dogsAdapter,loading)
                    if(result){
                        showLoader()
                        if(filter.isNotEmpty()){
                            dogsViewModel.getDogsByBreed(filter)
                        }else{
                            dogsViewModel.getDogsImages()
                        }

                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }
    /**
     * Función que se ejecuta cuando el usuario quiere realizar una busqueda de algún libro
     * en especifico
     */
    private fun searchBreed() {
        with(binding) {
            searchPhrase.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                @SuppressLint("NewApi")
                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard()
                    searchPhrase.clearFocus()
                    search(query)
                    return true
                }

                @RequiresApi(Build.VERSION_CODES.M)
                override fun onQueryTextChange(newText: String?): Boolean {
//                    search(newText)
                    return true
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun search(query:String?){

        showLoader()
        dogsAdapter.deleteData()
        if (!query.isNullOrEmpty()) {
            filter = query
            dogsViewModel.getDogsByBreed(query)
        }else{
            dogsViewModel.getDogsImages()
        }
        hideLoader()

    }
    /**
     * Función que se ejecuta apenas el usuario inicia una busqueda, esta oculta el teclado
     */
    private fun hideKeyboard() {
        if (view != null) {
            //Aquí esta la magia
            val input =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            input.hideSoftInputFromWindow(requireView().windowToken, 0)
            val menu: BottomNavigationView = requireActivity().findViewById(R.id.bnvMenu)
            menu.visibility =  View.VISIBLE
        }
    }
    private fun showNotFoundLayout() {
        binding.layoutNotFound.notFoundLayout.visibility = View.VISIBLE
    }
    private  fun hideNotFoundLayout(){
        binding.layoutNotFound.notFoundLayout.visibility = View.GONE
    }

    private fun showLoader(){
        binding.swipeContainerDogs.isRefreshing = true
        loading = true

    }

    private  fun hideLoader(){
        binding.swipeContainerDogs.isRefreshing = false
        loading = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCLickListener(data: Dog) {
        println("Click in $data")
        val bundle = bundleOf("info" to data)
        findNavController().navigate(R.id.navDialogDogs, bundle)

    }
}