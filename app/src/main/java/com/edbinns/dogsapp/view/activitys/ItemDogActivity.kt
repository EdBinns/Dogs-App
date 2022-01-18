package com.edbinns.dogsapp.view.activitys

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
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
import com.edbinns.dogsapp.view.adapters.DogsAdapter
import com.edbinns.dogsapp.view.adapters.ItemClickListener
import com.edbinns.dogsapp.viewmodel.DogsViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent
import android.graphics.Color
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.edbinns.dogsapp.utils.*
import com.edbinns.dogsapp.utils.Constants.CHANNEL_ID
import com.edbinns.dogsapp.utils.Constants.CHANNEL_NAME
import com.edbinns.dogsapp.utils.Constants.NOTIFICATION_ID


@AndroidEntryPoint
class ItemDogActivity : AppCompatActivity(), ItemClickListener<Dog> {

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
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)
    }
    private var clicked : Boolean = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDogBinding.inflate(layoutInflater)
        val view = binding.root
        getInfo()
        setInfo(dog)
        clickAddFavorite(dog)

        dogsViewModel.validateFavorite(dog)
        binding.fab.setOnClickListener { view ->
            onAddButtonClick()
        }
        binding.rvDogs.apply {
            layoutManager = manager
            adapter = dogsAdapter
            setHasFixedSize(false);
            isNestedScrollingEnabled = false;
        }
        binding.swipeContainerDogs.setOnRefreshListener {
            dogsAdapter.deleteData()
            if (isConnected()) {
                dogsViewModel.getDogsByBreed(dog.breed)
                hideLayout()
            } else {
                hideLoader()
                showLayout()
            }
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun observe() {
        dogsViewModel.imagesList.observe(this, Observer { list ->
            if (list.isNullOrEmpty()) {
                showNotFoundLayout()
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

        dogsViewModel.errorMessage.observe(this, Observer { messageType ->
            MessageFactory.getSnackBar(messageType, binding.root).show()
            hideLoader()
            showNotFoundLayout()
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

    private fun getInfo() {
        val data = intent
        dog = data.extras?.get("info") as Dog
    }

    private fun setInfo(dog: Dog) {
        Glide.with(this)
            .load(dog.imageURL)
            .override(Target.SIZE_ORIGINAL)
            .into(binding.ivDogPhotoItem)
        binding.tvDogBreed.text = dog.splitBreed()
    }

    private fun isConnected(): Boolean {
        return NetWorkConnectivity.checkForInternet(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showLoader() {
        binding.swipeContainerDogs.isRefreshing = true
        loading = true
        if (isConnected()) {
            dogsViewModel.getDogsByBreed(dog.breed)
        } else {
            MessageFactory.getSnackBar(MessageType.NETWORKCONNECTIOERRORMESSAGE, binding.root)
            hideLoader()
        }
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

    private fun showLayout() {
        binding.layoutInternetProblems.notFoundLayout.visibility = View.VISIBLE
    }

    private fun hideLayout() {
        binding.layoutInternetProblems.notFoundLayout.visibility = View.GONE
    }

    private fun onAddButtonClick() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }
    private fun setAnimation(clicked :Boolean) {
        with(binding){
            if(!clicked){
                fabDownload.startAnimation(fromBottom)
                btnAddFavorite.startAnimation(fromBottom)
                fab.startAnimation(rotateOpen)
            }else{
                fabDownload.startAnimation(toBottom)
                btnAddFavorite.startAnimation(toBottom)
                fab.startAnimation(rotateClose)
            }
        }

    }

    private fun setVisibility(clicked :Boolean) {
        with(binding){
            if(!clicked){
                fabDownload.visibility = View.VISIBLE
                btnAddFavorite.visibility = View.VISIBLE
            }else{
                fabDownload.visibility = View.INVISIBLE
                btnAddFavorite.visibility = View.INVISIBLE
            }
        }

    }

    private fun setClickable(clicked :Boolean){
        with(binding){
            if(!clicked){
                fabDownload.isClickable = true
                btnAddFavorite.isClickable = true
            }else{
                fabDownload.isClickable = false
                btnAddFavorite.isClickable = false
            }
        }

    }

    override fun onCLickListener(data: Dog) {
        val bundle = bundleOf("info" to data)
        val intent = Intent(this, ItemDogActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }



}