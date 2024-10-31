package com.example.layout.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.layout.core.BaseActivity
import com.example.layout.databinding.ActivityMainBinding
import com.example.layout.ui.theme.LayoutTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModelClass = MainViewModel::class.java
    override fun getViewDataBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {

    }

    override fun registerObservers() {

    }

}