package com.example.examcountdown

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nvt.color.ColorPickerDialog
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class ExamCreate : AppCompatActivity() {

    //firebase
    //private lateinit var binding : ActivityMainBinding
    private lateinit var database : DatabaseReference
    //clickable
    private lateinit var btnPickDate : TextView
    private lateinit var btnPickTime : TextView
    private lateinit var btnAppearance : TextView
    private lateinit var btnIcon : ImageButton
    private lateinit var btnCreate : Button
    //formatting
    private val formatDate = SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN)
    private val formatTime = SimpleDateFormat("HH:mm")
    private val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALIAN)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_exam)

        //back icon
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Add Exam"

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val min = c.get(Calendar.MINUTE)
        c.add(Calendar.HOUR_OF_DAY, 1)//CET
        //println("exam create "+c.time)

        //set current day ad text
        btnPickDate = findViewById(R.id.btn_pick_date)
        btnPickDate.text = formatDate.format(c.time)
        btnPickTime = findViewById(R.id.btn_pick_hour)
        btnPickTime.text = formatTime.format(c.time)

        //date picker
        btnPickDate.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                val x = Calendar.getInstance()
                x.set(year, month, dayOfMonth)
                btnPickDate.text = formatDate.format(x.time)
            }, year, month, day).show()
        }

        //time picker
        btnPickTime.setOnClickListener {
            val tpd = TimePickerDialog( this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                val x = Calendar.getInstance()
                x.set(1, 1, 1, hourOfDay, minute) //need only hours and min, so other field is set to 1
                btnPickTime.text = formatTime.format(x.time)
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

        btnCreate.setOnClickListener {
            val subject = subjectView.text.toString()
            val title = titleView.text.toString()
            val examDay = btnPickDate.text.toString()
            val time = btnPickTime.text.toString()
            val dateString : String = "$examDay $time"
            val date : Date = sdf.parse(dateString)
            //val color = btnIcon.background.toString()
            val colorDrawable : ColorDrawable = btnIcon.background as ColorDrawable
            val color : Int = colorDrawable.color

            if (subject.trim().isNotEmpty() ||
                    subject.trim().isNotBlank()) {
                println("exam: $subject $title time: $date color: $color")
                //create new exam (subject, opt: title, date, hour, color)

                database = FirebaseDatabase.getInstance("https://examcountdown-13b60-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Exams")
                val examTimestamp : Timestamp = Timestamp(date.time)
                //val exam = Exam(subject, title, date, color)
                val examDB = ExamDB(subject, title, Timestamp(date.time), color)
                database.child("$subject,$title").setValue(examDB).addOnSuccessListener {
                    println("success")
                }.addOnFailureListener {
                    println("failed")
                }
                val intent = Intent (this, MainActivity::class.java)
                startActivity(intent)
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