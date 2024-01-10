package com.example.sogating.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sogating.R

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        //1. joinBtn 을 가져온다
        /*
            방법1
            val joinBtn = findViewById<Button>(R.id.joinBtn)

            방법2
            val joinBtn : Button = findViewById(R.id.joinBtn)
        */

        val joinBtn = findViewById<Button>(R.id.joinBtn)

        joinBtn.setOnClickListener {

            //2. joinActivity 화면을 intent 에 넣고
            val intent = Intent(this, JoinActivity::class.java)

            //3. joinActivity 로 이동
            startActivity(intent)
        }

    }
}