package kr.ac.jbnu.se.minmamom.still_alive.util

/**
 * Copyright 2018 MINMAMOM All rights reserved.
 *
 * @author bongO moon
 * @since 2018. 05. 12.
 */
import android.app.Application
import com.tsengvn.typekit.Typekit




class GlobalApplication : Application() {

    private var instance : GlobalApplication? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "font/BMHANNA_11yrs_ttf.ttf"))
                .addCustom1(Typekit.createFromAsset(this, "font/BMHANNA_11yrs_ttf.ttf"))
        //        .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunGothic-Bold.otf"))
    }

}