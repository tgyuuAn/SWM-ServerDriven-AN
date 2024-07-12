package com.swm.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.swm.presentation.adapter.HomeContentAdapter
import com.swm.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val activityViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var homeContentAdapter: HomeContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        lifecycleScope.launch {
            activityViewModel.apply {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    screen.collect { homeContentAdapter.setContents(it.contents) }
                }

                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    richTextScreen.collect { Log.d("test", it.toString()) }
                }
            }
        }

        binding.apply {
            lifecycleOwner = this@MainActivity
            viewModel = activityViewModel
        }

        initRecyclerView()
    }

    // Recyclerview init
    private fun initRecyclerView() = binding.apply {
        homeContentAdapter = HomeContentAdapter()
        recyclerTitle.layoutManager = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerTitle.adapter = homeContentAdapter
    }
}