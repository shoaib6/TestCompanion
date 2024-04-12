package com.example.testcompanion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class SectionsAdapter(private val sectionsActivity: SectionsActivity, private val sectionNames: ArrayList<String>) : RecyclerView.Adapter<SectionsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sectionNo: TextView = itemView.findViewById(R.id.sectionNo)
        val sectionButton: ConstraintLayout = itemView.findViewById(R.id.sectionButton)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sections, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionsAdapter.ViewHolder, position: Int) {
        holder.sectionNo.text = sectionNames[position]
        holder.sectionButton.setOnClickListener {
            sectionsActivity.goToQuizActivity(position)
        }
    }

    override fun getItemCount(): Int {
       return sectionNames.size
    }
}