package com.example.showdogandmove

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.showdogandmove.databinding.ActivityEntryBinding


class EntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntryBinding

    private var userName: String? = null
    private var userEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        binding.btnEntry.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userName", binding.etName.text.toString())
            intent.putExtra("userEmail", binding.etEmail.text.toString())

            startActivity(intent)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        return super.dispatchTouchEvent(ev)
    }
}