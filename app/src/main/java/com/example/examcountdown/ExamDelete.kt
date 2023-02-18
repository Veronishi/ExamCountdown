package com.example.examcountdown

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import javax.security.auth.Subject

class ExamDelete  : AppCompatActivity() {

    private lateinit var deleteBackground : ConstraintLayout
    private lateinit var deleteBTN : Button
    private lateinit var subject : TextView
    private lateinit var title : TextView
    private lateinit var date : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        deleteBackground = findViewById(R.id.deleteBackground)
        deleteBTN = findViewById(R.id.delete_btn)
        subject = findViewById(R.id.subject_exam)
        title = findViewById(R.id.name_exam)
        date = findViewById(R.id.date_exam)

        //get info from previous activity
        val subjectText = intent.getStringExtra("subject")
        val titleText = intent.getStringExtra("title")
        val dateText = intent.getStringExtra("date")

        //setting info from previous activity
        val deleteBackgroundColor = intent.getIntExtra("examBackground", -1)
        deleteBackground.setBackgroundColor(deleteBackgroundColor)
        subject.text = subjectText
        title.text = titleText
        date.text = dateText

        deleteBTN.setOnClickListener {
            //delete exam
        }
    }
}
