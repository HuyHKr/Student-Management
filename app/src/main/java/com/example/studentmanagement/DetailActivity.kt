package com.example.studentmanagement

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.studentmanagement.databinding.ActivityDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mssv = intent.getStringExtra("mssv")
        val hoten = intent.getStringExtra("hoten")
        val ngaysinh = intent.getStringExtra("ngaysinh")
        val quequan = intent.getStringExtra("quequan")
        binding.detailMssv.text = mssv
        binding.detailHoten.text = hoten
        binding.detailNgaysinh.text = ngaysinh
        binding.detailQuequan.text = quequan
        binding.detailEmail.text = GlobalFunction.getEmail(hoten!!,mssv!!)
        val currentStudent = Student(mssv,hoten,ngaysinh!!,quequan!!)
        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode== Activity.RESULT_OK){
                val mssv = it.data?.getStringExtra("mssv")
                val hoten = it.data?.getStringExtra("hoten")
                binding.detailMssv.text = mssv
                binding.detailHoten.text = hoten
                binding.detailNgaysinh.text = it.data?.getStringExtra("ngaysinh")
                binding.detailQuequan.text = it.data?.getStringExtra("quequan")
                binding.detailEmail.text = GlobalFunction.getEmail(hoten!!,mssv!!)
            }
        }
        binding.update.setOnClickListener{
            val intent = Intent(this@DetailActivity,AddStudentActivity::class.java)
            intent.putExtra("mode","update")
            intent.putExtra("mssv",mssv)
            intent.putExtra("hoten",hoten)
            intent.putExtra("ngaysinh",ngaysinh)
            intent.putExtra("quequan",quequan)
            launcher.launch(intent)
        }
        binding.delete.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO){
                val studentDao = StudentDatabase.getInstance(applicationContext).studentDao()
                try{
                    studentDao.delete(currentStudent)

                    withContext(Dispatchers.Main){
                        Toast.makeText(this@DetailActivity,"Xóa thành công, đang quay trở lại ứng dụng...",Toast.LENGTH_SHORT).show()
                        delay(1000L)
                        finish()
                    }
                }catch(e:Exception){
                    Toast.makeText(this@DetailActivity,"Xóa Thất bại",Toast.LENGTH_SHORT).show()

                }
            }
        }


    }
}