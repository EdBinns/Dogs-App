package com.edbinns.dogsapp.view.activitys

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edbinns.dogsapp.R
import com.edbinns.dogsapp.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, MainActivity::class.java)
        shoAnim(intent)

    }

    private fun shoAnim(intent: Intent) {

        binding.animViewFav.apply {
            setAnimation(R.raw.animdog)
            repeatCount = 3
            playAnimation()
            addAnimatorListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    startActivity(intent)
                    finish()
                }
            })
        }

    }
}