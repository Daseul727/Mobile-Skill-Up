package com.example.sogating

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.sogating.auth.IntroActivity
import com.example.sogating.slider.CardStackAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {

    private lateinit var cardStackAdapter: CardStackAdapter
    private lateinit var manager : CardStackLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val setting = findViewById<ImageView>(R.id.settingIcon)
        setting.setOnClickListener{
            val auth = Firebase.auth
            auth.signOut()

            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }

        val cardStackView = findViewById<CardStackView>(R.id.cardStackView)


        /*
        1. object 마우스 커서 두고 override
        manager = CardStackLayoutManager(baseContext, object : CardStackListener {

        })*/
        manager = CardStackLayoutManager(baseContext, object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {
            }

            override fun onCardSwiped(direction: Direction?) {
            }

            override fun onCardRewound() {
            }

            override fun onCardCanceled() {
            }

            override fun onCardAppeared(view: View?, position: Int) {
            }

            override fun onCardDisappeared(view: View?, position: Int) {
            }

        })

        val testList = mutableListOf<String>()
        testList.add("a")
        testList.add("b")
        testList.add("c")

        //이 부분이 CardStackAdapter 에서 들어오는 파라미터
        cardStackAdapter = CardStackAdapter(baseContext,testList)
        cardStackView.layoutManager = manager
        cardStackView.adapter = cardStackAdapter

    }
}