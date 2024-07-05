package com.swm.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.swm.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val activityViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        binding.viewModel = activityViewModel
        binding.lifecycleOwner = this

        binding.recyclerTitle.adapter = HomeContentAdapter()

        // viewModel에서 데이터 받아오기
        /*
        viewModel.contents.observe(viewLifecycleOwner, Observer { contents ->
            adapter.setContents(contents)
        })
        viewModel.fetchContents()
        */
    }
}