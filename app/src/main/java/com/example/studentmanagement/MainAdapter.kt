package com.example.studentmanagement

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(val listItems:ArrayList<Student>): RecyclerView.Adapter<MainAdapter.MyViewHolder>(){
    class MyViewHolder(val view: View):RecyclerView.ViewHolder(view){
        val name = view.findViewById<TextView>(R.id.name)
        val mssv = view.findViewById<TextView>(R.id.mssv)
        val email = view.findViewById<TextView>(R.id.email)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hoten = listItems[position].hoten
        val mssv = listItems[position].mssv
        holder.name.text = hoten
        holder.mssv.text = mssv
        holder.email.text = getEmail(hoten,mssv)
        Log.v("TAG","onBindView successful!")
    }
    fun getEmail(hoten:String,mssv:String):String{
        if(hoten==""||mssv=="") return ""

        val listEntry = hoten.split(" ").map{removeAccent(it.lowercase())}
        var result = listEntry[0]+"."
        for((index,entry) in listEntry.withIndex()){
            if(index!=0){
                result+=entry[0]
            }
        }
        result+=mssv.substring(2,8)
        Log.v("TAG","email: ${result+"@sis.hust.edu.vn"}")
        return result+"@sis.hust.edu.vn"
    }
    fun removeAccent(s: String): String {
        val original = arrayOf("á", "à", "ả", "ã", "ạ", "ă", "ắ", "ằ", "ẳ", "ẵ", "ặ", "â", "ấ", "ầ", "ẩ", "ẫ", "ậ", "đ", "é", "è", "ẻ", "ẽ", "ẹ", "ê", "ế", "ề", "ể", "ễ", "ệ", "í", "ì", "ỉ", "ĩ", "ị", "ó", "ò", "ỏ", "õ", "ọ", "ô", "ố", "ồ", "ổ", "ỗ", "ộ", "ơ", "ớ", "ờ", "ở", "ỡ", "ợ", "ú", "ù", "ủ", "ũ", "ụ", "ư", "ứ", "ừ", "ử", "ữ", "ự", "ý", "ỳ", "ỷ", "ỹ", "ỵ")
        val replacement = arrayOf("a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "d", "e", "e", "e", "e", "e", "e", "e", "e", "e", "e", "e", "i", "i", "i", "i", "i", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "u", "u", "u", "u", "u", "u", "u", "u", "u", "u", "u", "y", "y", "y", "y", "y")

        var result = s
        for (i in original.indices) {
            result = result.replace(original[i], replacement[i])
            result = result.replace(original[i].uppercase(), replacement[i].uppercase())
        }
        return result
    }

}