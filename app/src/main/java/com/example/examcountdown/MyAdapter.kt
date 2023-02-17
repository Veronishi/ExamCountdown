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

    private val sdf: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALY)

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
        holder.backGround.setBackgroundColor(currentItem.color)
        val date : Date = sdf.parse(""+currentItem.date+" "+currentItem.time)
        //val currentDate: Date = Date()
        val c : Calendar = Calendar.getInstance()
        //c.timeZone = TimeZone.getTimeZone("CET")
        c.add(Calendar.HOUR_OF_DAY, 1) //CET
        println(c.time)
        val today : Date = c.time
        val diff : Long = date.time - today.time
        val differenceInMinutes = (TimeUnit.MILLISECONDS.toMinutes(diff) % 60)
        val differenceInHours = (TimeUnit.MILLISECONDS.toHours(diff) % 24)
        val differenceInDays = (TimeUnit.MILLISECONDS.toDays(diff) % 365)
        println("$date - $today = $differenceInDays days, $differenceInHours hours, $differenceInMinutes minutes")
        if (differenceInDays <= 0) {
            if(differenceInHours > 0){
                holder.remDays.text = differenceInHours.toString()
                holder.textView.text = "hours until"
            } else {
                holder.remDays.text = "\u2713" //unicode check mark
                holder.textView.text = "completed"
            }
        } else holder.remDays.text = differenceInDays.toString()//(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1).toString()
    }

    override fun getItemCount(): Int {
        return examList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //pick elements in exam_item
        val subject : TextView = itemView.findViewById(R.id.textView8)
        val title : TextView = itemView.findViewById(R.id.textView9)
        val date : TextView = itemView.findViewById(R.id.textView12)
        val backGround : LinearLayout = itemView.findViewById(R.id.backgound)
        val remDays : TextView = itemView.findViewById(R.id.textView10)
        val textView : TextView = itemView.findViewById(R.id.textView11)

    }
}