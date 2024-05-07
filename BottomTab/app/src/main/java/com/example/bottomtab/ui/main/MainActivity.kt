package com.example.bottomtab.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.bottomtab.Contents
import com.example.bottomtab.R
import com.example.bottomtab.databinding.ActivityMainBinding
import com.example.bottomtab.ui.board.BoardFragment
import com.example.bottomtab.ui.home.HomeFragment
import com.example.bottomtab.ui.mypage.MypageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation(savedInstanceState)

    }

    /**
     * 하단 navigation setting
     */
    private fun initBottomNavigation(savedInstanceState: Bundle?) {

        //앱 최초실행
        if (savedInstanceState == null) {
            binding.navArea.selectedItemId = R.id.nav_home
            openFragment(HomeFragment(), Contents.HOME)
        }

        binding.navArea.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_home -> {
                    openFragment(HomeFragment(), Contents.HOME)
                    true
                }

                R.id.nav_board -> {
                    openFragment(BoardFragment(), Contents.BOARD)
                    true
                }

                R.id.nav_mypage -> {
                    openFragment(MypageFragment(), Contents.MYPAGE)
                    true
                }
                else -> false
            }
        }
    }

    /**
     * fragment 이동
     */
    private fun openFragment(fragment: Fragment, tag: String) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(tag)
        transaction.commit()
    }

}