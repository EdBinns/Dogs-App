package com.edbinns.dogsapp.view.fragments

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.edbinns.dogsapp.R

import com.edbinns.dogsapp.databinding.FragmentFavoriteBinding
import com.edbinns.dogsapp.models.Dog
import com.edbinns.dogsapp.models.Favorite
import com.edbinns.dogsapp.services.room.toDogsList
import com.edbinns.dogsapp.view.activitys.ItemDogActivity
import com.edbinns.dogsapp.view.adapters.DogsAdapter
import com.edbinns.dogsapp.view.adapters.ItemClickListener
import com.edbinns.dogsapp.viewmodel.FavoriteViewModel
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(), ItemClickListener<Dog> {


    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val dogsAdapter: DogsAdapter by lazy {
        DogsAdapter(this)
    }

    private val manager: LinearLayoutManager by lazy {
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        with(binding.rvDogs) {
            layoutManager = manager
            adapter = dogsAdapter
        }
        binding.swipeContainerDogs.setOnRefreshListener {
            dogsAdapter.deleteData()
            favoriteViewModel.getAllFavorites()
        }
        loadAds()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoader()
        observe()
    }
    private fun loadAds(){
        val adRequest = AdRequest.Builder().build()
        binding.banner.loadAd(adRequest)
    }
    private fun observe() {
        favoriteViewModel.favoritesList.observe(viewLifecycleOwner, Observer { list ->
            observeLogic(list)
        })
        favoriteViewModel.mutableList.observe(viewLifecycleOwner, Observer { list ->
            observeLogic(list)
        })
    }

    private fun observeLogic(list: List<Favorite>) {
        dogsAdapter.deleteData()

        if (list.isNullOrEmpty()) {
            showNotFoundLayout()
        } else {
            hideNotFoundLayout()
        }

        dogsAdapter.updateData(list.toDogsList())
        hideLoader()
    }

    private fun showLoader() {
        binding.swipeContainerDogs.isRefreshing = true
    }

    private fun hideLoader() {
        binding.swipeContainerDogs.isRefreshing = false
    }

    private fun showNotFoundLayout() {
        binding.layoutNotFound.notFoundLayout.visibility = View.VISIBLE
        binding.layoutNotFound.tvNotFound.text = " Aun no cuentas con razas favoritas"
    }

    private fun hideNotFoundLayout() {
        binding.layoutNotFound.notFoundLayout.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCLickListener(data: Dog) {
        val bundle = bundleOf("info" to data)
        val intent = Intent(activity, ItemDogActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

}