package com.example.examcountdown.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examcountdown.Exam
import com.example.examcountdown.MyAdapter
import com.example.examcountdown.R
import com.google.firebase.database.*

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    private lateinit var dbref : DatabaseReference
    private lateinit var examRecyclerView : RecyclerView
    private lateinit var examArrayList: ArrayList<Exam>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
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
        val a = inflater.inflate(R.layout.create_exam, container, false)
        examRecyclerView = root.findViewById(R.id.examList)
        examRecyclerView.layoutManager = LinearLayoutManager(this.context)
        examRecyclerView.setHasFixedSize(true)

        examArrayList = arrayListOf<Exam>()
        getUserData()
        return root
    }

    companion object {
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
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance("https://examcountdown-13b60-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Exams")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val exam =  userSnapshot.getValue(Exam::class.java)
                        //TODO("filter by past and future exam")
                        examArrayList.add(exam!!)
                    }
                    examRecyclerView.adapter = MyAdapter(examArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}