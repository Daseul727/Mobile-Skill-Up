package com.example.sogating.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.sogating.MainActivity
import com.example.sogating.R
import com.example.sogating.auth.UserDataModel
import com.example.sogating.utils.FirebaseAuthUtils
import com.example.sogating.utils.FirebaseRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MyPageActivity : AppCompatActivity() {

    private val TAG = "MyPageActivity ===== "
    private val uid = FirebaseAuthUtils.getUid()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        getMyData()

        val myMainBtn = findViewById<Button>(R.id.myMainBtn)
        myMainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getMyData() {

        val myImage = findViewById<ImageView>(R.id.myImage)
        val myUid = findViewById<TextView>(R.id.myUid)
        val myNickname = findViewById<TextView>(R.id.myNickname)
        val myAge = findViewById<TextView>(R.id.myAge)
        val myCity = findViewById<TextView>(R.id.myCity)
        val myGender = findViewById<TextView>(R.id.myGender)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.w(TAG, dataSnapshot.toString())
                var data = dataSnapshot.getValue(UserDataModel::class.java)

                myUid.text = data!!.uid
                myNickname.text = data!!.nickname
                myAge.text = data!!.age
                myCity.text = data!!.city
                myGender.text = data!!.gender

                //myImage
                val storageRef = Firebase.storage.reference.child(data.uid  +".png")
                storageRef.downloadUrl.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Glide.with(baseContext)
                            .load(task.result)
                            .into(myImage)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userInfoRef.child(uid).addValueEventListener(postListener)
    }
}