package com.edbinns.dogsapp.view.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edbinns.dogsapp.R
import com.edbinns.dogsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
}