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
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * A placeholder fragment containing a simple view.
 */
class FutureFragment : Fragment() {

    //private lateinit var pageViewModel: PageViewModel

    private lateinit var dbref: DatabaseReference
    private lateinit var examRecyclerView: RecyclerView
    private lateinit var examArrayList: ArrayList<Exam>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }*/
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        /*val textView: TextView = root.findViewById(R.id.section_label)
        pageViewModel.text.observe(this, Observer<String> {
            textView.text = it
        })*/
        examRecyclerView = root.findViewById(R.id.examList)
        examRecyclerView.layoutManager = LinearLayoutManager(this.context)
        examRecyclerView.setHasFixedSize(true)

        examArrayList = arrayListOf<Exam>()
        getExamData()
        return root
    }

    /*companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): FutureFragment {
            return FutureFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }*/

    private fun getExamData() {
        dbref = FirebaseDatabase.getInstance("https://examcountdown-13b60-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Exams")
        dbref.orderByChild("date/time").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (examSnapshot in snapshot.children) {
                        val exam = examSnapshot.getValue(Exam::class.java)
                        //val sdf: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
                        val date: Date = Date(exam!!.date.time) //sdf.parse(""+exam!!.date+" "+exam!!.time)
                        val currentDate: Date = Date()
                        val diff: Long = date.time - currentDate.time
                        /*val differenceInMinutes = (TimeUnit.MILLISECONDS.toMinutes(diff) % 60)
                        val differenceInHours = (TimeUnit.MILLISECONDS.toHours(diff) % 24)
                        val differenceInDays = (TimeUnit.MILLISECONDS.toDays(diff) % 365)
                        println("$date - $currentDate = $differenceInDays days, $differenceInHours hours, $differenceInMinutes minutes")*/
                        if (diff >= 0) examArrayList.add(exam)
                    }
                    examRecyclerView.adapter = MyAdapter(examArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    //TODO("implement delete exam")
}