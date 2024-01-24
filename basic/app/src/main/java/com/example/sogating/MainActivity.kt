package com.example.sogating

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.sogating.auth.UserDataModel
import com.example.sogating.setting.SettingActivity
import com.example.sogating.slider.CardStackAdapter
import com.example.sogating.utils.FirebaseAuthUtils
import com.example.sogating.utils.FirebaseRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {

    private lateinit var cardStackAdapter: CardStackAdapter
    private lateinit var manager : CardStackLayoutManager

    private val TAG = "MainActivity !!! "
    private var userList = mutableListOf<UserDataModel>()

    private var userCount = 0

    private val uid = FirebaseAuthUtils.getUid()
    private lateinit var currentUserGender : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val setting = findViewById<ImageView>(R.id.settingIcon)
        setting.setOnClickListener{

            val intent = Intent(this, SettingActivity::class.java)
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

                if (direction == Direction.Right) {
                    //LENGTH_SHORT = 토스트 쳬류시간
                    Toast.makeText(this@MainActivity, "오른쪽 입니다", Toast.LENGTH_SHORT).show()
                }

                if (direction == Direction.Left) {
                    Toast.makeText(this@MainActivity, "왼쪽 입니다", Toast.LENGTH_SHORT).show()
                }

                userCount += 1

                if (userCount == userList.count()) {
                    Toast.makeText(this@MainActivity, "유저 새롭게 받아옵니다", Toast.LENGTH_SHORT).show()
                    getUserDataList(currentUserGender)
                }

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

        //이 부분이 CardStackAdapter 에서 들어오는 파라미터
        cardStackAdapter = CardStackAdapter(baseContext,userList)
        cardStackView.layoutManager = manager
        cardStackView.adapter = cardStackAdapter

        //getUserDataList()
        getMyUserData()
    }
    private fun getMyUserData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var data = dataSnapshot.getValue(UserDataModel::class.java)

                currentUserGender = data?.gender.toString()
                getUserDataList(currentUserGender)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userInfoRef.child(uid).addValueEventListener(postListener)
    }

    private fun getUserDataList(currentUserGender : String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //val post = dataSnapshot.getValue<Post>()
                //Log.d(TAG,dataSnapshot.toString())

                for (dataModel in dataSnapshot.children) {
                    //Log.d(TAG, dataModel.toString())

                    val user = dataModel.getValue(UserDataModel::class.java)

                    if (user!!.gender.toString() != currentUserGender) {
                        userList.add(user)
                    }

                }

                //카드 어댑터 동기화
                cardStackAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userInfoRef.addValueEventListener(postListener)
    }

}