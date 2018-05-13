package kr.ac.jbnu.se.minmamom.still_alive.activity

/**
 * Copyright 2018 All rights reserved by MINMAMOM.
 *
 * @author bongO moon
 * @since 2018. 05. 12.
 */

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_splash_main.*
import kr.ac.jbnu.se.minmamom.still_alive.R
import kr.ac.jbnu.se.mobileapp.activity.base.ToolbarBaseActivity


class SplashMainActivity : ToolbarBaseActivity() {
    private val SPLASH_TIME_OUT = 3500
    private val TAG: String = "SplashMainActivity"
    private var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_main)

        val image1 = findViewById(R.id.bird) as ImageView
        val imageViewTarget1 = GlideDrawableImageViewTarget(image1)
        Glide.with(this).load(R.raw.walk).into(image1)

        mAuth = FirebaseAuth.getInstance()
        setSurviveNum(mAuth!!.currentUser)

        Handler().postDelayed(object:Runnable {
            override fun run(){
                var home = Intent(this@SplashMainActivity, MainActivity::class.java)
                home.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(home)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }, SPLASH_TIME_OUT.toLong())
    }

    fun setSurviveNum(user: FirebaseUser?) {
        if(user != null){
            mFirestore.collection("users")
                    .document(user?.email.toString())
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val document = task.result
                            if (document != null) {
                                survive.setText(task.result.data.get("survive").toString() + "  일 차")
                                Log.d(TAG, "DocumentSnapshot data: " + task.result.data.get("survive").toString())
                            } else {
                                Log.d(TAG, "No such document")
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.exception)
                        }
                    }
        }
        else
            survive.setText("0  일 차")
    }

}
