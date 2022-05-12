package com.upes.employeeonboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class EmpLogin : AppCompatActivity() {
    private lateinit var proceed : Button
    private lateinit var back : TextView
    private lateinit var email : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_login)
        email = findViewById(R.id.empEmail)


        proceed = findViewById(R.id.proceed_details)
        back = findViewById(R.id.registerFooter)
        proceed.setOnClickListener {
            if(email.text.toString() == "test@test.com"){
                Toast.makeText(this, "Make sure you are allowed to onboard...", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, EmpDashboard::class.java)
                startActivity(intent)
                finish()
            }
        }

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}