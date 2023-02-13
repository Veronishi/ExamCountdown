package com.example.examcountdown

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.examcountdown.databinding.ActivityMainBinding
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
import com.nvt.color.ColorPickerDialog
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class ExamCreate : AppCompatActivity() {

    //firebase
    //private lateinit var binding : ActivityMainBinding
    //private lateinit var database : DatabaseReference
    //clickable
    private lateinit var btnPickDate : TextView
    private lateinit var btnPickTime : TextView
    private lateinit var btnAppearance : TextView
    private lateinit var btnIcon : ImageButton
    private lateinit var btnCreate : Button
    //formatting
    private var formatDate = SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN)
    private var formatTime = SimpleDateFormat("HH:mm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_exam)

        //back icon
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Add Exam"

        //date picker
        btnPickDate = findViewById(R.id.btn_pick_date)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        //set current day ad text
        btnPickDate.text = formatDate.format(c.time)//" $day/${month+1}/$year "

        btnPickDate.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                val x = Calendar.getInstance()
                x.set(year, month, dayOfMonth)
                btnPickDate.text = formatDate.format(x.time)
            }, year, month, day).show()
        }

        //time picker
        btnPickTime = findViewById(R.id.btn_pick_hour)

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val min = c.get(Calendar.MINUTE)

        btnPickTime.text = formatTime.format(c.time)//" $hour:$min "

        btnPickTime.setOnClickListener {
            val tpd = TimePickerDialog( this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                val x = Calendar.getInstance()
                x.set(1, 1, 1, hourOfDay, minute)
                btnPickTime.text = formatTime.format(x.time) //need only hours and mins, so other field is set to 1
            }, hour, min, true).show()
        }

        //appearance
        btnAppearance = findViewById(R.id.textView3)
        btnIcon = findViewById(R.id.imageButton)

        btnAppearance.setOnClickListener {
            changeAppearance()
        }
        btnIcon.setOnClickListener {
            changeAppearance()
        }

        //exam creation
        val subjectView : TextView = findViewById(R.id.editTextTextPersonName2)
        val titleView : TextView = findViewById(R.id.editTextTextPersonName3)
        //btnPickDate
        //btnPickTime
        //btnIcon

        btnCreate = findViewById(R.id.button)

        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        btnCreate.setOnClickListener {
            val subject = subjectView.text.toString()
            val title = titleView.text.toString()
            val date = btnPickDate.text.toString()
            val time = btnPickTime.text.toString()
            val color = btnIcon.background.toString()

            if (subject.trim().isNotEmpty() ||
                    subject.trim().isNotBlank()) {
                println("exam: $subject $title time: $date $time color: $color")
                //create new exam (subject, opt: title, date, hour, color)
                //database = FirebaseDatabase.getInstance().getReference("Exams")
                val exam = Exam(subject, title, date, time, color)
                /*database.child(subject).setValue(exam).addOnSuccessListener {
                    println("success")
                }.addOnFailureListener {
                    println("failed")
                }*/
            } else {
                Toast.makeText(this, "Please enter a subject", Toast.LENGTH_SHORT).show()
                println("Please enter a subject")
            }
        }
    }

    private fun changeAppearance() {
        val colorPicker = ColorPickerDialog(
            this,
            Color.BLACK, // color init
            true, // true is show alpha
            object : ColorPickerDialog.OnColorPickerListener {
                override fun onCancel(dialog: ColorPickerDialog?) {
                    // handle click button Cancel
                }

                override fun onOk(dialog: ColorPickerDialog?, colorPicker: Int) {
                    // handle click button OK
                    btnIcon.setBackgroundColor(colorPicker)
                }
            })
        colorPicker.show()
    }
}