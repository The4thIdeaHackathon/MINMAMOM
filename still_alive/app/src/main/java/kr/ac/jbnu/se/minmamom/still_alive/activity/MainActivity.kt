package kr.ac.jbnu.se.minmamom.still_alive.activity

/**
 * Copyright 2018 All rights reserved by MINMAMOM.
 *
 * @author bongO moon
 * @since 2018. 05. 12.
 */

import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kr.ac.jbnu.se.minmamom.still_alive.R
import kr.ac.jbnu.se.minmamom.still_alive.service.InitMusicService
import kr.ac.jbnu.se.mobileapp.activity.base.ToolbarBaseActivity


class MainActivity : ToolbarBaseActivity() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        updateUI(mAuth!!.currentUser)
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
