package com.example.showdogandmove.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.showdogandmove.MainActivity
import com.example.showdogandmove.R
import com.example.showdogandmove.databinding.FragmentMypageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : Fragment() {

    private var _binding : FragmentMypageBinding ? = null
    private val binding : FragmentMypageBinding
        get() = _binding!!

    companion object {
        fun newInstance() : MyPageFragment {
            val fragment = MyPageFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_mypage, container, false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            incTitlebar.txtTitle.text = "MY PAGE"

            incTitlebar.btnMypage.visibility = View.GONE
            incTitlebar.btnBack.visibility = View.VISIBLE
            incTitlebar.btnBack.setOnClickListener {
                val act = activity as MainActivity
                act.navigateUp("MY")

            }
        }
    }
}