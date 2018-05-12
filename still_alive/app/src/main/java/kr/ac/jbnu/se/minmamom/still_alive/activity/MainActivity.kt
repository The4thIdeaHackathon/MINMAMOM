package kr.ac.jbnu.se.minmamom.still_alive.activity

/**
 * Copyright 2018 All rights reserved by MINMAMOM.
 *
 * @author bongO moon
 * @since 2018. 05. 12.
 */

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kr.ac.jbnu.se.minmamom.still_alive.R
import kr.ac.jbnu.se.minmamom.still_alive.service.InitMusicService
import kr.ac.jbnu.se.mobileapp.activity.base.ToolbarBaseActivity


class MainActivity : ToolbarBaseActivity() {
    private val TAG: String = "MainActivity"
    private var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        updateUI(mAuth!!.currentUser)

//        val image1 = findViewById(R.id.walkman) as ImageView
//        val imageViewTarget1 = GlideDrawableImageViewTarget(image1)
//        Glide.with(this).load(R.raw.walk).into(image1)
//
//        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.translate)
//
//        image1.animation = animation

        setQuest(mAuth!!.currentUser)
    }

    fun onClickedCapsule(v: View) {
        var nextintent = Intent(this, SplashTimeCapsuleActivity::class.java)
        nextintent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(nextintent)
    }

    fun onClickedQuest(v: View) {
        var nextintent = Intent(this, DoingQuestActivity::class.java)
        nextintent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(nextintent)
    }

    fun setQuest(user: FirebaseUser?) {
        if(user != null){
            mFirestore.collection("mission")
                    .document("20180513")
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val document = task.result
                            if (document != null) {
                                quest_title.setText(task.result.data.get("title").toString())
                                quest_description.setText(task.result.data.get("description").toString())
                                Log.d(TAG, "DocumentSnapshot data: " + task.result.data.get("title").toString())
                            } else {
                                Log.d(TAG, "No such document")
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.exception)
                        }
                    }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user == null) {
            var nextintent = Intent(this, LoginActivity::class.java)
            nextintent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(nextintent)
        } else {
            stopService(Intent(applicationContext, InitMusicService::class.java))
        }
    }
}
