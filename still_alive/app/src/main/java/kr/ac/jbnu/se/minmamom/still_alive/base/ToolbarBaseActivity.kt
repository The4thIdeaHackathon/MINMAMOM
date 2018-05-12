package kr.ac.jbnu.se.mobileapp.activity.base

/**
 * Copyright 2018 All rights reserved by WaySeekers.
 *
 * @author bongO moon
 * @since 2018. 04. 17.
 */

import android.app.ActivityManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.tsengvn.typekit.TypekitContextWrapper
import kotlinx.android.synthetic.main.toolbar.*
import kr.ac.jbnu.se.minmamom.still_alive.R

abstract class ToolbarBaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareTaskDescription()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }

    private fun prepareTaskDescription() {
        if (Build.VERSION.SDK_INT >= 21) {
            if (sTaskDescription == null) {
                val label = getString(R.string.app_name)
                val icon = BitmapFactory.decodeResource(resources, R.drawable.ic_noimage)
                val colorPrimary = getColor(R.color.colorPrimary)
                sTaskDescription = ActivityManager.TaskDescription(label, icon, colorPrimary)
            }
            setTaskDescription(sTaskDescription)
        }
    }

    companion object {
        private var sTaskDescription: ActivityManager.TaskDescription? = null
    }
}

