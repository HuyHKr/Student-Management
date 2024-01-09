package com.example.studentmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    var recyclerView:RecyclerView?=null
    override fun onResume() {
        if(recyclerView!=null){
            buildData()
        }
        super.onResume()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        buildData()
    }
    fun buildData(){//khởi tạo recyclerview
        lifecycleScope.launch(Dispatchers.IO){
            val studentDao = StudentDatabase.getInstance(applicationContext).studentDao()
            val students = studentDao.getAllStudents()
            val listItems = arrayListOf<Student>()//chuyen Array Thanh ArrayList
            for(entry in students){
                Log.v("TAG","entry: ${entry.mssv}")
                listItems.add(entry)
            }
            Log.v("TAG","Trong RecyclerView, so student: ${students.size}")
            withContext(Dispatchers.Main){
                recyclerView?.adapter = MainAdapter(listItems)
                recyclerView?.layoutManager = LinearLayoutManager(this@MainActivity)
            }

        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.addButton){
            Log.v("TAG","addButton Clicked")
            val intent = Intent(this,AddStudentActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}