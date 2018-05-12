package kr.ac.jbnu.se.minmamom.still_alive.util
/**
 * Copyright 2018 All rights reserved by MINMAMOM.
 *
 * @author bongO moon
 * @since 2018. 05. 12.
 */
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import kr.ac.jbnu.se.minmamom.still_alive.R

class FragmentUtil(fm: FragmentManager? = null, containerId: Int? = 0){
    private val TAG = FragmentUtil::class.java!!.getSimpleName()
    private var mFragmentManager: FragmentManager? = fm
    private var mContainerId: Int? = containerId

    fun add(fragment: Fragment) {
        if (mContainerId == 0) {
            Log.e(TAG, "Fragment Container Layout Id is zero.")
            return
        }

        add(mContainerId!!, fragment, null)
    }

    fun add(fragment: Fragment, b: Bundle) {
        if (mContainerId == 0) {
            Log.e(TAG, "Fragment Container Layout Id is zero.")
            return
        }

        add(mContainerId!!, fragment, b)
    }

    fun add(containerId: Int, fragment: Fragment, b: Bundle?) {
        if (mFragmentManager == null) {
            Log.e(TAG, "FragmentManager fm is null.")
            return
        }

        if (b != null) {
            fragment.arguments = b
        }

        if (mFragmentManager!!.fragments.size != 0) {
            mFragmentManager!!.beginTransaction()
                    .setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.fragment_in_backstack, R.anim.fragment_out_backstack)
                    .replace(containerId, fragment)
                    .addToBackStack(fragment.toString())
                    .commitAllowingStateLoss()
        } else {
            mFragmentManager!!.beginTransaction()
                    .add(containerId, fragment)
                    .commitAllowingStateLoss()
        }
    }

    fun back() {
        mFragmentManager?.popBackStack()
    }
}