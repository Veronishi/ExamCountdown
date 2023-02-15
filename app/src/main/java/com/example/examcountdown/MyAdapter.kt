package com.example.examcountdown

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MyAdapter(private val examList: ArrayList<Exam>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var sdf: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.exam_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = examList[position]
        holder.subject.text = currentItem.subject
        holder.title.text = currentItem.title
        val time = ""+currentItem.date+" "+currentItem.time //format dd/mm/yyyy hh:mm
        holder.date.text = time
        holder.backgroud.setBackgroundColor(currentItem.color)
        val date : Date = sdf.parse(currentItem.date)
        val currentDate : Date = Date()
        val diff: Long = date.time - currentDate.time
        println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))
        if (diff < 0) {
            holder.remDays.text = "\u2713" //unicode checkmark
            holder.textView.text = "Complete"
        } else holder.remDays.text = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toString()

        //val days: Int = Days.daysBetween(date, currentDate).getDays()
        //TODO("calculate missing days")
        //holder.color.background = currentItem.color
    }

    override fun getItemCount(): Int {
        return examList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //pick elements in exam_item
        val subject : TextView = itemView.findViewById(R.id.textView8)
        val title : TextView = itemView.findViewById(R.id.textView9)
        val date : TextView = itemView.findViewById(R.id.textView12)
        val backgroud : LinearLayout = itemView.findViewById(R.id.backgound)
        val remDays : TextView = itemView.findViewById(R.id.textView10)
        val textView : TextView = itemView.findViewById(R.id.textView11)
        //val color : ImageButton = itemView.findViewById(R.id.imageButton)

    }
}