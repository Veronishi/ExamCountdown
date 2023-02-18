package com.example.examcountdown

import android.content.Intent
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
        val time = sdf.format(currentItem.date)
        holder.date.text = time.toString()
        holder.backGround.setBackgroundColor(currentItem.color)
        val date = currentItem.date
        val currentDate = Date()
        val diff : Long = date.time - currentDate.time
        val differenceInMinutes = (TimeUnit.MILLISECONDS.toMinutes(diff) % 60)
        val differenceInHours = (TimeUnit.MILLISECONDS.toHours(diff) % 24)
        val differenceInDays = (TimeUnit.MILLISECONDS.toDays(diff) % 365)
        println("$date - $currentDate = $differenceInDays days, $differenceInHours hours, $differenceInMinutes minutes")
        if (differenceInDays <= 0) {
            when {
                differenceInHours > 0 -> {
                    holder.remDays.text = differenceInHours.toString()
                    holder.textView.text = "hours until"
                }
                differenceInMinutes > 0 -> {
                    holder.remDays.text = differenceInMinutes.toString()
                    holder.textView.text = "minutes until"
                }
                else -> {
                    holder.remDays.text = "\u2713" //unicode check mark
                    holder.textView.text = "completed"
                }
            }
        } else holder.remDays.text = differenceInDays.toString()
        holder.backGround.setOnClickListener { v ->
            val intent = Intent(v.context, ExamDelete::class.java)
            //send information no new activity
            intent.putExtra("subject", holder.subject.text)
            intent.putExtra("title", holder.title.text)
            intent.putExtra("date", (holder.date.text))
            intent.putExtra("examBackground", currentItem.color)
            v.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return examList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //pick elements in exam_item
        val subject : TextView = itemView.findViewById(R.id.textView8)
        val title : TextView = itemView.findViewById(R.id.textView9)
        val date : TextView = itemView.findViewById(R.id.textView12)
        val backGround : LinearLayout = itemView.findViewById(R.id.examBackground)
        val remDays : TextView = itemView.findViewById(R.id.textView10)
        val textView : TextView = itemView.findViewById(R.id.textView11)

    }
}