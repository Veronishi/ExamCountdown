package com.example.examcountdown

import java.sql.Timestamp
import java.util.Date

    data class Exam(val subject: String? = null, val title: String? = null, val date: Date = Date(-1), val color: Int = -1)
    data class ExamDB(val subject: String? = null, val title: String? = null, val date: Timestamp = Timestamp(-1), val color: Int = -1)
//(subject, opt: title, date, color)
