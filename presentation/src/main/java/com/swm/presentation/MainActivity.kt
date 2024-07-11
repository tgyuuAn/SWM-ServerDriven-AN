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

        // ✅ RichText test
        binding.viewModel?.getRichTextScreen()
        binding.viewModel!!.richTextScreen
            .onEach {
                // ✅ 데이터가 잘 받아와지는지 log 찍어보는 부분입니다! presentation 구현하실 때 지우셔도 됩니다
                Log.d("rich text", it.toString())
                Log.d("rich text content 길이", it.responseData.contents.size.toString())
                if(it.responseData.contents.size == 3) {
                    Log.d("rich text > RichViewType", it.responseData.contents[2].content.toString());
                }
            }
            .launchIn(lifecycleScope)
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