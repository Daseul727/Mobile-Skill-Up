package com.example.sogating.auth

import android.content.Intent
import android.graphics.Bitmap
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class JoinActivity : AppCompatActivity() {

    private val TAG = "JoinActivity"

    private lateinit var auth: FirebaseAuth

    private var email = ""
    private var password = ""
    private var nickname = ""
    private var gender = ""
    private var city = ""
    private var age = ""
    private var uid = ""

    lateinit var profileImage : ImageView

    /*
        참고 : https://firebase.google.com/docs/auth/android/start?hl=ko#kotlin+ktx
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = Firebase.auth

        //이미지 불러오기
        profileImage = findViewById(R.id.imageArea)
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

            val emailArea = findViewById<TextInputEditText>(R.id.emailArea)
            val passwordArea = findViewById<TextInputEditText>(R.id.passwordArea)

            email = emailArea.text.toString()
            password = passwordArea.text.toString()
            nickname = findViewById<TextInputEditText>(R.id.nicknameArea).text.toString()
            gender = findViewById<TextInputEditText>(R.id.genderArea).text.toString()
            city = findViewById<TextInputEditText>(R.id.cityArea).text.toString()
            age = findViewById<TextInputEditText>(R.id.ageArea).text.toString()

            //Log.d(TAG, email.text.toString())
            //Log.d(TAG, password.text.toString())

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        //Log.d(TAG, "createUserWithEmail:success")

                        val user = auth.currentUser
                        uid = user?.uid.toString()
                        //Log.d(TAG, user?.uid.toString())

                        //토큰 받아옴
                        FirebaseMessaging.getInstance().token.addOnCompleteListener(
                            OnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                //Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                                return@OnCompleteListener
                            }

                            // Get new FCM registration token
                            val token = task.result

                            //Log.e(TAG, token.toString())

                            //1. 단순 message 저장
                            //FirebaseRef.userInfoRef.setValue("Hello, World!!!!!")

                            //2. uid 하위에 message 저장
                            //FirebaseRef.userInfoRef.child(uid).setValue("Hello, World!!!!!")

                            //3. uid 하위에 data 다발 저장
                            val userModel = UserDataModel(email, nickname,gender, city, age, uid, token)
                            FirebaseRef.userInfoRef.child(uid).setValue(userModel)

                            //4. upload profile image to stoarage
                            uploadImage(uid)

                        })


                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    }
                }
        }
    }

    private fun uploadImage(uid : String) {

        val storage = Firebase.storage
        val storageRef = storage.reference.child(uid + ".png")

        // Get the data from an ImageView as bytes
        profileImage.isDrawingCacheEnabled = true
        profileImage.buildDrawingCache()
        val bitmap = (profileImage.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = storageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }
}