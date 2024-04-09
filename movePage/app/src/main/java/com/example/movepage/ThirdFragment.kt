package com.example.movepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.movepage.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {
    private var _binding : FragmentThirdBinding? = null
    private val binding : FragmentThirdBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_third, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            incTitlebar2.btnBack.setOnClickListener {
                val act = activity as MainActivity
                act.navigateUp("3")
            }

            btnHome.setOnClickListener {
                val act = activity as MainActivity
                act.clearBackStack()
            }

            incTitlebar2.txtTitle.text = "Third"
        }
    }
}