package kr.ac.jbnu.se.minmamom.still_alive.handler

import android.app.Activity
import android.widget.Toast



class BackPressCloseHandler {
    private var backKeyPressedTime: Long = 0
    private var toast: Toast? = null;
    private var activity: Activity? = null

    constructor(context: Activity){
        this.activity = context;
    }

    fun onBackPressed(): Int {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return 1;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity?.finish()
            toast?.cancel()
            return 2
        }
        return 0;
    }

    fun showGuide() {
        toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
        toast?.show()
    }
}