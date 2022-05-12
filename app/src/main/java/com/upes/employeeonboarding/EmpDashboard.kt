package com.upes.employeeonboarding

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.w3c.dom.Text

class EmpDashboard : AppCompatActivity() {
    private lateinit var name : EditText
    private lateinit var govtIDNo : EditText
    private lateinit var mobileNo : EditText
    private lateinit var uploadGovtID : TextView
    private lateinit var fileURl : String
    private lateinit var submit : Button

    val storage = Firebase.storage("gs://employee-onboarding-337cf.appspot.com")
    var storageRef = storage.reference
    val PICK_IMAGE_REQUEST = 71

    val db = Firebase.firestore

    private fun chooseGovtID(){
        val intent = Intent()
        intent.type = "*/*"

        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            val filePath = data!!.data!!
            val storageReference = storageRef.child("employeeDetails/${filePath.lastPathSegment}")
            val uploadTask = storageReference.putFile(filePath)
            Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
                Toast.makeText(this, "Failed to Upload File to Cloud", Toast.LENGTH_SHORT).show()
            }.addOnSuccessListener { taskSnapshot ->
                fileURl = taskSnapshot.uploadSessionUri.toString()
                val details = hashMapOf(
                    "name" to name.text.toString(),
                    "govtIDNo" to govtIDNo.text.toString(),
                    "mobile" to mobileNo.text.toString(),
                    "govtID" to fileURl
                )

                db.collection("employeeDetails").document("abc@test.com").set(details).addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_dashboard)
        name = findViewById(R.id.legalName)
        govtIDNo = findViewById(R.id.IdNo)
        mobileNo = findViewById(R.id.mobileNo)
        uploadGovtID = findViewById(R.id.uploadID)
        submit = findViewById(R.id.submitDetails)
        uploadGovtID.setOnClickListener {
            chooseGovtID()
        }
        submit.setOnClickListener {
            val intent = Intent(this, Success::class.java)
            startActivity(intent)
            finish()
        }
    }
}