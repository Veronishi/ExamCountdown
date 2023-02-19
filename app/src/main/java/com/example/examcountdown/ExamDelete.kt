package com.example.examcountdown

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ExamDelete : AppCompatActivity() {
    //firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    //graphic elements
    private lateinit var deleteBackground: ConstraintLayout
    private lateinit var deleteBTN: Button
    private lateinit var subject: TextView
    private lateinit var title: TextView
    private lateinit var date: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        supportActionBar!!.title = "Delete Exam"

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
            val examID = "$subjectText,$titleText"
            deleteExam(examID)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun deleteExam(examID: String) {
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser == null) auth.signInAnonymously()
        database =
            FirebaseDatabase.getInstance("https://examcountdown-13b60-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Exams").child(auth.currentUser?.uid.toString())
        database.child(examID).removeValue().addOnSuccessListener {
            Toast.makeText(this, "Successfully deleted", Toast.LENGTH_SHORT).show()
            println("delete success")
        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            println("delete failed")
        }
    }
}
