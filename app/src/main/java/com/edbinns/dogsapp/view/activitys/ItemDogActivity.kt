package com.edbinns.dogsapp.view.activitys

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.edbinns.dogsapp.R
import com.edbinns.dogsapp.databinding.ActivityItemDogBinding
import com.edbinns.dogsapp.databinding.ActivityMainBinding
import com.edbinns.dogsapp.models.Dog
import com.edbinns.dogsapp.utils.splitBreed
import com.edbinns.dogsapp.view.adapters.DogsAdapter
import com.edbinns.dogsapp.view.adapters.ItemClickListener
import com.edbinns.dogsapp.viewmodel.DogsViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.edbinns.dogsapp.utils.toNonNullable


@AndroidEntryPoint
class ItemDogActivity : AppCompatActivity() , ItemClickListener<Dog> {

    private lateinit var binding: ActivityItemDogBinding

    private var loading = false
    private var isFavorite = false

    private lateinit var dog: Dog
    private val dogsAdapter: DogsAdapter by lazy {
        DogsAdapter(this)
    }
    private val manager: StaggeredGridLayoutManager by lazy {
        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }
    private val dogsViewModel: DogsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDogBinding.inflate(layoutInflater)
        val view = binding.root

        getInfo()
        setInfo(dog)
        clickAddFavorite(dog)

        dogsViewModel.validateFavorite(dog)
        binding.rvDogs.apply{
            layoutManager = manager
            adapter = dogsAdapter
            setHasFixedSize(false);
            isNestedScrollingEnabled = false;
        }
        binding.swipeContainerDogs.setOnRefreshListener {
            dogsAdapter.deleteData()
            dogsViewModel.getDogsByBreed(dog.breed)
        }
        binding.ivExit.setOnClickListener {
            finish()
        }
        setContentView(view)
        showLoader()
        scrollPaging()
        observe()
    }




    private fun scrollPaging() {
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
        dogsViewModel.imagesList.observe(this, Observer { list ->
            if (list.isNullOrEmpty()) {
                showNotFoundLayout()
//                EMPTY_LIST.showMessage(requireView(), R.color.warning_color)
            } else
                hideNotFoundLayout()
            dogsAdapter.updateData(list)
            hideLoader()
        })

        dogsViewModel.isFavorite.observe(this, Observer { result ->
            isFavorite = result
            println("favorite observer $isFavorite")
            if (isFavorite) {
                binding.btnAddFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.btnAddFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
        })
    }

    private fun clickAddFavorite(item: Dog) {
        binding.btnAddFavorite.setOnClickListener {
            if (isFavorite) {
                binding.btnAddFavorite.setImageResource(R.drawable.ic_favorite_border)
                dogsViewModel.deleteFavorite(item)
            } else {
                binding.btnAddFavorite.setImageResource(R.drawable.ic_favorite)
                dogsViewModel.addFavorite(item)
            }
        }
    }

    private fun getInfo(){
        val data = intent
        dog = data.extras?.get("info") as Dog
//        (data.getBundleExtra("info") as Dog?).also { dog = i }
    }
    private fun setInfo(dog: Dog) {
        Glide.with(this)
            .load(dog.imageURL)
            .into(binding.ivDogPhotoItem)
        binding.tvDogBreed.text = dog.splitBreed()
    }
    private fun showLoader() {
        binding.swipeContainerDogs.isRefreshing = true
        loading = true
        dogsViewModel.getDogsByBreed(dog.breed)
    }

    private fun hideLoader() {
        binding.swipeContainerDogs.isRefreshing = false
        loading = false
    }

    private fun showNotFoundLayout() {
        binding.layoutNotFound.notFoundLayout.visibility = View.VISIBLE
    }

    private fun hideNotFoundLayout() {
        binding.layoutNotFound.notFoundLayout.visibility = View.GONE
    }
    override fun onCLickListener(data: Dog) {
        val bundle = bundleOf("info" to data)
        val intent = Intent(this, ItemDogActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}