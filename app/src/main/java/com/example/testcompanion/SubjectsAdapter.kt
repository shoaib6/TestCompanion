package com.example.testcompanion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SubjectsAdapter(private val subjectsName: List<String>, private val subjectsImageList: ArrayList<Int>, private val subjectsActivity: SubjectsActivity) :
    RecyclerView.Adapter<SubjectsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val subjectName: TextView = view.findViewById(R.id.subjectName)
        val subjectsImageView: ImageView = view.findViewById(R.id.subjectsImageView)
        val btnSubject: LinearLayout = view.findViewById(R.id.btnSubject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subjects, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.subjectName.text = subjectsName[position]
        holder.subjectsImageView.setImageResource(subjectsImageList[position])
        holder.btnSubject.setOnClickListener {
            Constant.Subject = subjectsName[position]
            subjectsActivity.goToSectionsActivity()
        }
    }

    override fun getItemCount(): Int {
        return subjectsName.size
    }

}