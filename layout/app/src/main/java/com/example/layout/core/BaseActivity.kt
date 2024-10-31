package com.example.layout.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {
    protected lateinit var viewModel: VM
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    abstract val viewModelClass: Class<VM>
    protected abstract fun getViewDataBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = getViewDataBinding()
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[viewModelClass]

        initView()
        registerObservers()

    }

    // 서브클래스에서 UI 요소 설정
    abstract fun initView()

    // 서브클래스에서 ViewModel 관찰
    abstract fun registerObservers()

}

