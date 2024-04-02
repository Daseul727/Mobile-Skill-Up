package com.example.showdog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.showdog.databinding.ActivityMainBinding
import com.example.showdog.utils.NetworkResult
import com.example.showdog.model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel
    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        initView()
        fetchData()

        _binding.imgRefresh.setOnClickListener {
            fetchResponse()
        }
    }

    private fun initView() {
        _binding.isDebug = true
    }

    private fun fetchData() {
        fetchResponse()

        mainViewModel.response.observe(this) { res->
            when (res) {
                is NetworkResult.Success -> {
                    res.data?.let {
                        _binding.item = res.data

                        _binding.imgDog.load(
                            res.data.message
                        ) {
                            transformations(RoundedCornersTransformation(16f))
                        }
                    }
                    _binding.pbDog.visibility = View.GONE
                }
                is NetworkResult.Error -> {
                    _binding.pbDog.visibility = View.GONE
                    Toast.makeText(
                        this,
                        res.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    _binding.pbDog.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun fetchResponse() {
        mainViewModel.fetchDogResponse()
        _binding.pbDog.visibility = View.VISIBLE
    }
}