package com.upes.employeeonboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class Dashboard : AppCompatActivity() {
    private lateinit var uploadBtn : Button
    private lateinit var logout : Button
    private lateinit var fileURl : String
    private lateinit var header : TextView
    var auth = FirebaseAuth.getInstance()

    val userName = auth.currentUser?.displayName;
    val storage = Firebase.storage("gs://employee-onboarding-337cf.appspot.com")
    var storageRef = storage.reference
    val PICK_IMAGE_REQUEST = 71
    private fun signOut(){
        auth.signOut();
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun chooseFile(){
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            val filePath = data!!.data!!
            val storageReference = storageRef.child("uploads/${filePath.lastPathSegment}")
            val uploadTask = storageReference.putFile(filePath)
            Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
                Toast.makeText(this, "Failed to Upload File to Cloud", Toast.LENGTH_SHORT).show()
            }.addOnSuccessListener { taskSnapshot ->
                fileURl = taskSnapshot.uploadSessionUri.toString()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        uploadBtn = findViewById(R.id.uploadFile);
        logout = findViewById(R.id.logout);
        header = findViewById(R.id.dashboardHeader)
        header.setText("Welcome " + userName)

        logout.setOnClickListener{
            signOut()
        }

        uploadBtn.setOnClickListener {
            chooseFile()
        }
    }
}