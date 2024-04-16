package com.example.showdogandmove.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.showdogandmove.MainActivity
import com.example.showdogandmove.R
import com.example.showdogandmove.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondFragment : Fragment() {

    private var _binding : FragmentSecondBinding? = null
    private val binding: FragmentSecondBinding
        get() = _binding!!

    var strParam = ""

    companion object {
        fun newInstance() : SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    fun setJsonParam() {
        val bundle = arguments
        bundle?.let {
            strParam = it.getString("param", "").toString()
            Log.d("TLOG - SecondFragment", "받은값 : $strParam")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_second,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setJsonParam()
        binding.apply {

            tvMessage.text = strParam

            incTitlebar.txtTitle.text = "Second"
            incTitlebar.btnBack.setOnClickListener {
                val act = activity as MainActivity
                act.navigateUp("2")

            }

            btnMove.setOnClickListener {
                val act = activity as MainActivity
                act.openFragment(ThirdFragment(), "3")
            }
        }
    }
}