package com.example.bottomtab.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bottomtab.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    companion object {
        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

    }
}