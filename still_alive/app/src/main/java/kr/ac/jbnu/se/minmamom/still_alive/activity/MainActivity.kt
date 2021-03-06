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
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
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

        val image1 = findViewById(R.id.walkman) as ImageView
        val imageViewTarget1 = GlideDrawableImageViewTarget(image1)
        Glide.with(this).load(R.raw.detective).into(image1)

        setQuest(mAuth!!.currentUser)
    }

    fun onClickedCapsule(v: View) {
        var nextintent = Intent(this, SplashTimeCapsuleActivity::class.java)
        nextintent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(nextintent)
    }

    fun onClickedQuest(v: View) {
        var nextintent = Intent(this, DoingQuestActivity::class.java)
        nextintent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(nextintent)
        overridePendingTransition(R.anim.fragment_in, R.anim.fragment_out)
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
                                if(task.result.data.get("isClear").toString() == "true"){
                                    quest_title.setText("오늘의 미션 완료")
                                    quest_description.setText("오늘 미션은 깔꼼달꼼하게 끝냈습니다. 내일 봐연")
                                    btn_mission.visibility = View.GONE
                                    btn_mission_com.visibility = View.VISIBLE
                                }else {
                                    quest_title.setText(task.result.data.get("title").toString())
                                    quest_description.setText(task.result.data.get("description").toString())
                                    btn_mission.visibility = View.VISIBLE
                                    btn_mission_com.visibility = View.GONE
                                    Log.d(TAG, "DocumentSnapshot data: " + task.result.data.get("title").toString())
                                }
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
