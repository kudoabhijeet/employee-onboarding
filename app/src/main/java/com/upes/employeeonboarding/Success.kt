package com.upes.employeeonboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Success : AppCompatActivity() {
    private lateinit var okayButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        okayButton = findViewById(R.id.done)
        okayButton.setOnClickListener {
            finish()
        }

    }
}