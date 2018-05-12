package kr.ac.jbnu.se.minmamom.still_alive.dialog

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.View
import kr.ac.jbnu.se.minmamom.still_alive.R

class MissionDialog() {

    private var context: Context? = null
    private var dialog: AlertDialog? = null

    constructor(context: Context): this(){
        this.context = context
    }

    fun show() {
        dialog = AlertDialog.Builder(context!!)
                .setView(getView())
                .show()
    }

    fun getView(): View {
        val v = View.inflate(context, R.layout.dialog_mission, null)
//        .setText(battle.senderName)
//        .setText(battle.exerciseName)
//
//        .setOnClickListener(View.OnClickListener {  })
//        .setOnClickListener(View.OnClickListener { dialog?.dismiss() })

        return v
    }
}
