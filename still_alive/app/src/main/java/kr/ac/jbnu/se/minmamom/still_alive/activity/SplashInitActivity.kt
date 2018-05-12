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
import kr.ac.jbnu.se.minmamom.still_alive.R
import kr.ac.jbnu.se.minmamom.still_alive.service.InitMusicService
import kr.ac.jbnu.se.mobileapp.activity.base.ToolbarBaseActivity


class SplashInitActivity : ToolbarBaseActivity() {
    val SPLASH_TIME_OUT = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_init)
        startService(Intent(applicationContext, InitMusicService::class.java))
        Handler().postDelayed(object:Runnable {
            public override fun run(){
                var home = Intent(this@SplashInitActivity, SplashMainActivity::class.java)
                startActivity(home)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }, SPLASH_TIME_OUT.toLong())
    }


}
