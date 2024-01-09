package com.example.studentmanagement

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {
    @Query("SELECT * FROM student")
    suspend fun getAllStudents():Array<Student>
    @Query("SELECT * FROM student WHERE mssv=:mssv")
    suspend fun getStudentByMssv(mssv:String):Array<Student>
    @Insert
    suspend fun insert(vararg student:Student)
    @Update
    suspend fun update(vararg student:Student)
    @Delete
    suspend fun delete(student: Student)

}