package com.example.studentmanagement

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date

@Entity(tableName = "student")
data class Student (
    @PrimaryKey val mssv:String,
    val hoten:String,
    val ngaysinh:String,
    val quequan:String
    )
