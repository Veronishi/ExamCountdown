package com.example.examcountdown

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.Drawable





class MyAdapter(private val examList: ArrayList<Exam>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

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
        //val color : ImageButton = itemView.findViewById(R.id.imageButton)

    }
}