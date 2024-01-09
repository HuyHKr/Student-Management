package com.example.studentmanagement

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.example.studentmanagement.databinding.ActivityAddStudentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.InputStream
import java.util.Calendar

class AddStudentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddStudentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val inputStream: InputStream = resources.openRawResource(R.raw.quequan)
        val reader = inputStream.reader()
        val content = reader.readText()
        val listTinh = content.split(",").map { it.trim() }
        Log.v("TAG","Tinh dau trong danh sach: ${listTinh[0]}")
        reader.close()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,listTinh)
        binding.quequanI.setAdapter(adapter)
        binding.birthdayI.setOnClickListener{
            //get current date
            val c = Calendar.getInstance()
            val cYear = c.get(Calendar.YEAR)
            val cMonth = c.get(Calendar.MONTH)
            val cDate = c.get(Calendar.DATE)
            //show datepicker
            val datePicker = DatePickerDialog(this,{ view: View, year:Int, month:Int, date:Int->
                binding.birthdayI.setText("${year}-${month+1}-${date}")
                Log.v("TAG","ngaysinh: ${binding.birthdayI.text.toString()}")
            },cYear,cMonth,cDate)
            datePicker.show()
        }

        binding.commitButton.setOnClickListener{
            val mssv = binding.mssvI.text.toString()
            val hoten = binding.nameI.text.toString()
            val quequan = binding.quequanI.text.toString()
            val ngaysinh = binding.birthdayI.text.toString()
            submit(mssv,hoten,ngaysinh,quequan)

        }
    }
    fun submit(mssv:String,hoten:String,ngaysinh:String,quequan:String){
         lifecycleScope.launch(Dispatchers.IO) {
             val studentDao = StudentDatabase.getInstance(applicationContext).studentDao()
             val students = studentDao.getStudentByMssv(mssv)
             Log.v("TAG","students size: ${students.size}")
             when{
                 mssv==""->{
                     binding.error.setTextColor(Color.argb(255,255,0,0))
                     binding.error.setText("Mssv không được để trống!")
                 }
                 hoten==""->{
                     binding.error.setTextColor(Color.argb(255,255,0,0))
                     binding.error.setText("Họ và tên không được để trống!")
                 }
                 students.size>0->{
                     binding.error.setTextColor(Color.argb(255,255,0,0))
                     binding.error.setText("Mssv đã tồn tại!")
                 }
                 else->{
                     Log.v("TAG","start insert...")
                    val newStudent = Student(mssv,hoten,ngaysinh,quequan)
                     studentDao.insert(newStudent)
                     binding.error.setTextColor(Color.argb(255,0,255,0))
                     binding.error.setText("Thêm thành công!")
                     delay(1000L)
                     finish()
                 }
             }
         }
    }
}