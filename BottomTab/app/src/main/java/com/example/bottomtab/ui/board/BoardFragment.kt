package com.example.bottomtab.ui.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.bottomtab.R
import com.example.bottomtab.databinding.FragmentBoardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardFragment : Fragment() {

    private var _binding: FragmentBoardBinding? = null
    private val binding: FragmentBoardBinding
        get() = _binding!!

    companion object {
        fun newInstance(): BoardFragment {
            val fragment = BoardFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_board, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ///
        }
    }

}