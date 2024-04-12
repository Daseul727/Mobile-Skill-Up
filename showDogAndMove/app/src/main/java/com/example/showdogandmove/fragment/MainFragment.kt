package com.example.showdogandmove.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.showdogandmove.MainActivity
import com.example.showdogandmove.R
import com.example.showdogandmove.databinding.FragmentMainBinding
import com.example.showdogandmove.model.MainViewModel
import com.example.showdogandmove.utils.NetworkResult
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var mainViewModel: MainViewModel //이걸 여기서? main activity 에서?
    
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_main, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            incTitlebar.btnBack.visibility = View.GONE
            incTitlebar.txtTitle.text = "MAIN"

            //이미지 새로고침
            binding.imgRefresh.setOnClickListener {
                fetchResponse()
            }

            fetchData()


            btnMoveSecond.setOnClickListener {
                val paramData = if(etMessage.text.isNotEmpty()) etMessage.text.toString() else "empty msg"
                val bundle = Bundle()
                bundle.putString("param", paramData)
                val act = activity as MainActivity
                act.openFragment(SecondFragment.newInstance().apply {
                    arguments = bundle
                }, "2")
            }

            btnMoveThird.setOnClickListener {
                val act = activity as MainActivity
                act.openFragment(ThirdFragment() , "3")
            }
        }
    }

    private fun fetchResponse() {
        mainViewModel.fetchDogResponse()
        binding.pbDog.visibility = View.VISIBLE
    }

    private fun fetchData() {
        fetchResponse()

        mainViewModel.response.observe(viewLifecycleOwner) { res->
            when (res) {
                is NetworkResult.Success -> {
                    res.data?.let {
                        binding.item = res.data

                        binding.imgDog.load(
                            res.data.message
                        ) {
                            transformations(RoundedCornersTransformation(16f))
                        }
                    }
                    binding.pbDog.visibility = View.GONE
                }
                is NetworkResult.Error -> {
                    binding.pbDog.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        res.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    binding.pbDog.visibility = View.VISIBLE
                }
            }
        }
    }
}