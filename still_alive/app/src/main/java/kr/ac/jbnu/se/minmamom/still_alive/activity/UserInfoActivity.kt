package kr.ac.jbnu.se.minmamom.still_alive.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_init_setup.*
import kr.ac.jbnu.se.minmamom.still_alive.R
import kr.ac.jbnu.se.mobileapp.activity.base.ToolbarBaseActivity


class UserInfoActivity : ToolbarBaseActivity() {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var userInfo: HashMap<String, Any> = HashMap<String, Any>()
    private val TAG = "UserInfoActivity"
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_setup)
        mAuth = FirebaseAuth.getInstance()

        btn_setup_user_next.setOnClickListener {
            var a: String = edit_age.getText().toString()
            var s1: Boolean = check1.isChecked()
            var s2: Boolean = check2.isChecked()

            if (a.isEmpty()) {
                Toast.makeText(this, "모든 칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            } else
                userInfo.put("age", a)
            if (s1 && s2) {
                Toast.makeText(this, "당신의 성별은 하나입니다.", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            } else {
                if (s1)
                    userInfo.put("sex", "man")
                else
                    userInfo.put("sex", "woman")
            }
            userInfo.put("survive", "1")
            userInfo.put("rivive", "3")
            userInfo.put("death", "0")

            sendUserData(mAuth!!.currentUser, userInfo)
            Toast.makeText(this, "훌륭해요! 지금부터 생존을 위해 노력해주세요!", Toast.LENGTH_SHORT).show();

            var home = Intent(this, SplashMainActivity::class.java)
            home.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(home)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun sendUserData(user: FirebaseUser?, userInfo: HashMap<String, Any>) {
        // Add a new document with a generated ID
        db.collection("users")
                .document(user?.email.toString())
                .set(userInfo)
                .addOnSuccessListener { documentReference -> Log.d(TAG, "DocumentSnapshot add!!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
    }
}