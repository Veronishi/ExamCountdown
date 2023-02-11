package com.example.examcountdown

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class ExamCreate : AppCompatActivity() {

    private lateinit var btnPickDate : TextView
    private lateinit var btnPickTime : TextView
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

    }
}