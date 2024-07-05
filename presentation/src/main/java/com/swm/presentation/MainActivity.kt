package com.swm.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.swm.domain.model.Screen
import com.swm.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

        binding.viewModel = activityViewModel
        binding.lifecycleOwner = this

        binding.viewModel?.getScreen()
        binding.viewModel!!.screen
            .onEach {
                initRecyclerView(it)
            }
            .launchIn(lifecycleScope)


        // binding.recyclerTitle.adapter = HomeContentAdapter()

        // viewModel에서 데이터 받아오기
        /*
        viewModel.contents.observe(viewLifecycleOwner, Observer { contents ->
            adapter.setContents(contents)
        })
        viewModel.fetchContents()
        */
    }

    // Recyclerview init
    private fun initRecyclerView(screen: Screen) {
        homeContentAdapter = HomeContentAdapter()
        binding.recyclerTitle.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerTitle.adapter = homeContentAdapter
        homeContentAdapter.setContents(screen.contents)
    }
}