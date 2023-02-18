package com.example.examcountdown.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examcountdown.Exam
import com.example.examcountdown.MyAdapter
import com.example.examcountdown.R
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class PastFragment : Fragment() {

    private lateinit var dbref: DatabaseReference
    private lateinit var examRecyclerView: RecyclerView
    private lateinit var examArrayList: ArrayList<Exam>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        examRecyclerView = root.findViewById(R.id.examList)
        examRecyclerView.layoutManager = LinearLayoutManager(this.context)
        examRecyclerView.setHasFixedSize(true)

        examArrayList = arrayListOf<Exam>()
        getExamData()
        return root
    }

    private fun getExamData() {
        dbref = FirebaseDatabase.getInstance("https://examcountdown-13b60-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Exams")
        dbref.orderByChild("date/time").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val exam = userSnapshot.getValue(Exam::class.java)
                        val date = Date(exam!!.date.time)
                        val currentDate = Date()
                        val diff: Long = date.time - currentDate.time
                        if (diff < 0) examArrayList.add(exam)
                    }
                    examArrayList.reverse()
                    examRecyclerView.adapter = MyAdapter(examArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}