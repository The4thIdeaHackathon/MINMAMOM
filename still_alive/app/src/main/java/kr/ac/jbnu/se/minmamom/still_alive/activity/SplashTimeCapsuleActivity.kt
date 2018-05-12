package kr.ac.jbnu.se.minmamom.still_alive.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import kr.ac.jbnu.se.minmamom.still_alive.R
import kr.ac.jbnu.se.mobileapp.activity.base.ToolbarBaseActivity

class SplashTimeCapsuleActivity : ToolbarBaseActivity() {
    val SPLASH_TIME_OUT = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_capsule_splash)

        Handler().postDelayed(object:Runnable {
            public override fun run(){
                var home = Intent(this@SplashTimeCapsuleActivity, TimeCapsuleActivity::class.java)
                startActivity(home)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }, SPLASH_TIME_OUT.toLong())
    }
}
