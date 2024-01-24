package com.example.sogating.auth

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.sogating.MainActivity
import com.example.sogating.R
import com.example.sogating.utils.FirebaseRef
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream

class JoinActivity : AppCompatActivity() {

    private val TAG = "JoinActivity"

    private lateinit var auth: FirebaseAuth

    private var nickname = ""
    private var gender = ""
    private var city = ""
    private var age = ""
    private var uid = ""

    /*
        참고 : https://firebase.google.com/docs/auth/android/start?hl=ko#kotlin+ktx
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = Firebase.auth

        //이미지 불러오기
        val profileImage = findViewById<ImageView>(R.id.imageArea)

        val getAction = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback { uri ->
                profileImage.setImageURI(uri)
            }
        )

        profileImage.setOnClickListener {
            getAction.launch("image/*")
        }

        //가입하기
        val joinBtn = findViewById<Button>(R.id.joinSubmit)
        joinBtn.setOnClickListener {

            val email = findViewById<TextInputEditText>(R.id.emailArea)
            val password = findViewById<TextInputEditText>(R.id.passwordArea)

            nickname = findViewById<TextInputEditText>(R.id.nicknameArea).text.toString()
            gender = findViewById<TextInputEditText>(R.id.genderArea).text.toString()
            city = findViewById<TextInputEditText>(R.id.cityArea).text.toString()
            age = findViewById<TextInputEditText>(R.id.ageArea).text.toString()

            //Log.d(TAG, email.text.toString())
            //Log.d(TAG, password.text.toString())

            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        //Log.d(TAG, "createUserWithEmail:success")

                        val user = auth.currentUser
                        uid = user?.uid.toString()
                        Log.d(TAG, user?.uid.toString())

                        //1. 단순 message
                        //FirebaseRef.userInfoRef.setValue("Hello, World!!!!!")

                        //2. uid 하위에 message
                        //FirebaseRef.userInfoRef.child(uid).setValue("Hello, World!!!!!")

                        //3. uid 하위에 data
                        val userModel = UserDataModel(nickname,gender, city, age, uid)
                        FirebaseRef.userInfoRef.child(uid).setValue(userModel)

                        /*val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)*/

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    }
                }
        }
    }
}