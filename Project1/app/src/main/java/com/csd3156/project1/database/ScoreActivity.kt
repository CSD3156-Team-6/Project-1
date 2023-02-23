package com.csd3156.project1.database

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.csd3156.project1.R
import com.csd3156.project1.database.height.HeightListAdapter
import com.csd3156.project1.database.height.HeightViewModel
import com.csd3156.project1.database.height.HeightViewModelFactory
import com.csd3156.project1.database.time.TimerViewModel
import com.csd3156.project1.database.time.TimerViewModelFactory
import com.csd3156.project1.database.time.TimerListAdapter
import com.csd3156.project1.databinding.ActivityScoreBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBinding

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { ScoreRoomDatabase.getDatabase(this, applicationScope) }
    private val repository by lazy { ScoreRepository(database.timeDao(), database.heightDao()) }

    private val timerViewModel: TimerViewModel by viewModels {
        TimerViewModelFactory(repository, UserPreferencesRepository.getInstance(this))
    }

    private val heightViewModel: HeightViewModel by viewModels {
        HeightViewModelFactory(repository, UserPreferencesRepository.getInstance(this))
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val heightRecyclerView = binding.listViewHeight
        val heightAdapter = HeightListAdapter()
        heightRecyclerView.adapter = heightAdapter
        heightRecyclerView.layoutManager = LinearLayoutManager(this)

        val timeRecyclerView = binding.listViewTime
        val timeAdapter = TimerListAdapter()
        timeRecyclerView.adapter = timeAdapter
        timeRecyclerView.layoutManager = LinearLayoutManager(this)

        timerViewModel.allTime.observe(this, Observer { timer ->
            timer?.let { timeAdapter.submitList(it) }
        })

        heightViewModel.allHeight.observe(this, Observer { height ->
            height?.let { heightAdapter.submitList(it) }
        })

        val resetBtn = findViewById<Button>(R.id.resetDataBtn)
        resetBtn.setOnClickListener {
            timerViewModel.clear()
            heightViewModel.clear()
        }
    }
}