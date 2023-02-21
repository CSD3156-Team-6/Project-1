package com.csd3156.project1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.csd3156.project1.databinding.ActivityMainBinding
import com.csd3156.project1.game.GameActivity
import com.csd3156.project1.game.Snake

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val startBtn = findViewById<ImageView>(R.id.startBtn)
        startBtn.setOnClickListener{
            // creating intent
            val intent = Intent(this, GameActivity::class.java)
            // start page activity
            startActivity(intent)
        }
    }
}
