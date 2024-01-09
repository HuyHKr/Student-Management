package com.example.studentmanagement

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagement.GlobalFunction.Companion.getEmail

class MainAdapter(val listItems:ArrayList<Student>): RecyclerView.Adapter<MainAdapter.MyViewHolder>(){
    lateinit var onClick:((Int)->Unit)
    fun setOnItemClick(f:((Int)->Unit)){
        onClick = f
    }
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
        holder.view.setOnClickListener{
            onClick(position)
        }
    }


}