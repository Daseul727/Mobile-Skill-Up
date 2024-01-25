package com.example.sogating.messsage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.example.sogating.R
import com.example.sogating.auth.UserDataModel
import com.example.sogating.utils.FirebaseAuthUtils
import com.example.sogating.utils.FirebaseRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

/**
 * 내가 좋아요 클릭 한 user 리스트
 */
class MyLikeListActivity : AppCompatActivity() {

    private val TAG = "MyLikeListActivity ==="
    private val uid = FirebaseAuthUtils.getUid()

    private val likeUserListUid = mutableListOf<String>()
    private val likeUserList = mutableListOf<UserDataModel>() //내가 좋아요 한 사람들의 상세정보

    lateinit var listViewAdapter: ListViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_like_list)

        //리스트
        val myLikeListView = findViewById<ListView>(R.id.myLikeListView)
        listViewAdapter = ListViewAdapter(this, likeUserList)
        myLikeListView.adapter = listViewAdapter

        //내가 좋아요 한 사람들
        getMyLikeList()


        //좋아요 목록 클릭 이벤트
        myLikeListView.setOnItemClickListener { parent, view, position, id ->
            //Log.d(TAG, likeUserList[position].uid.toString())
            checkMatching(likeUserList[position].uid.toString())
        }

    }

    /**
     * 내가 좋아요한 사람 list
     */
    private fun getMyLikeList() {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataModel in dataSnapshot.children) {
                    likeUserListUid.add(dataModel.key.toString())
                }
                getUserDataList()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        FirebaseRef.userLikeRef.child(uid).addValueEventListener(postListener)

    }

    /**
     * Firebase에 등록된 user List 가져오기
     */
    private fun getUserDataList() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataModel in dataSnapshot.children) {
                    val user = dataModel.getValue(UserDataModel::class.java)

                    //만약 내가 좋아요 한 사람 이라면 리스트에 추가
                    if (likeUserListUid.contains(user?.uid)) {
                        likeUserList.add(user!!)
                    }
                }

                listViewAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userInfoRef.addValueEventListener(postListener)
    }

    private fun checkMatching (otherUid : String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.children.count() == 0) {
                    Toast.makeText(this@MyLikeListActivity, "매칭이 되지않았습니다", Toast.LENGTH_SHORT).show()
                } else {
                    //내가 좋아요 한 사람의 좋아요 리스트 = dataSnapshot 에서 나를 찾음
                    for (dataModel in dataSnapshot.children) {
                        val likeUserKey = dataModel.key.toString()
                        if (likeUserKey.equals(uid)) {
                            Toast.makeText(this@MyLikeListActivity, "매칭이 되었습니다", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        FirebaseRef.userLikeRef.child(otherUid).addValueEventListener(postListener)
    }
}