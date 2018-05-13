package kr.ac.jbnu.se.minmamom.still_alive.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_doing_quest.*
import kotlinx.android.synthetic.main.activity_main.*
import kr.ac.jbnu.se.minmamom.still_alive.R
import kr.ac.jbnu.se.mobileapp.activity.base.ToolbarBaseActivity
import java.io.IOException
import java.util.*

class DoingQuestActivity : ToolbarBaseActivity(), View.OnClickListener {

    private val TAG: String = "DoingQuestActivity"
    private var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var mAuth: FirebaseAuth? = null
    private var filePath: Uri? = null
    internal var storage: FirebaseStorage? =null
    internal var storageReference: StorageReference? = null
    private val PICK_IMAGE_REQUEST = 1234

    override fun onClick(p0: View?) {
        if(p0 === btn_choose)
            showFileChooser()
        else if(p0 === btn_upload)
            uploadFile()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST &&
                resultCode == Activity.RESULT_OK &&
                data != null && data.data != null){
            filePath = data.data
            try{
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                img_upload!!.setImageBitmap(bitmap)

            } catch (e: IOException){
                e.printStackTrace()
            }
        }
    }
    private fun uploadFile() {
        if(filePath != null){
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val imageRef = storageReference!!.child("images/" + mAuth!!.currentUser?.email.toString() + "/" + UUID.randomUUID().toString())
            imageRef.putFile(filePath!!)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "File Uploaded", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener() {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                    }
                    .addOnProgressListener { taskSnapshot ->
                        val progress = 100.0 * taskSnapshot.bytesTransferred/taskSnapshot.totalByteCount
                        progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
                    }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doing_quest)

        mAuth = FirebaseAuth.getInstance()

        //Init FireStorage
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        //Setup Button
        btn_choose.setOnClickListener(this)
        btn_upload.setOnClickListener(this)
    }

    private fun showFileChooser(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "SELECT PICTURE"), PICK_IMAGE_REQUEST)
    }

    fun setQuest(user: FirebaseUser?) {
        if(user != null){
            mFirestore.collection("mission")
                    .document("20180513")
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val document = task.result
                            if (document != null) {
                                quest_title.setText(task.result.data.get("title").toString())
                                quest_description.setText(task.result.data.get("description").toString())
                                Log.d(TAG, "DocumentSnapshot data: " + task.result.data.get("title").toString())
                            } else {
                                Log.d(TAG, "No such document")
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.exception)
                        }
                    }
        }
    }
}
