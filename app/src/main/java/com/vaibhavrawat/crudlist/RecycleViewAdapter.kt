package com.vaibhavrawat.recyclerlistproject

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vaibhavrawat.crudlist.ListClickInterface
import com.vaibhavrawat.crudlist.NotesEntity
import com.vaibhavrawat.crudlist.R
import org.w3c.dom.Text


class RecyclerViewAdapter(var dataList: ArrayList<NotesEntity>, var listClickInterface: ListClickInterface) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val btnUpdate: Button = view.findViewById(R.id.btnUpdate)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
        var title : TextView = view.findViewById(R.id.tvName)
        var description: TextView = view.findViewById(R.id.Roll)
        //var description : TextView = view.findViewById(R.id.Description)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataList[position].title
        holder.description.text = dataList[position].description.toString()
        //holder.description.text = dataList[position].description.toString()
        holder.btnUpdate.setOnClickListener {
            listClickInterface.onUpdateClick(position)
        }
//            val studentData = dataList[position]
//            holder.name.text = studentData.name
//            holder.roll.text = studentData.rollNo.toString()

        holder.btnDelete.setOnClickListener {
            listClickInterface.onDelete(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
