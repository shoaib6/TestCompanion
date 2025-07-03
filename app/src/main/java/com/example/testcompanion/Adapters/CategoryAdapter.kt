package com.example.testcompanion.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testcompanion.MainActivity
import com.example.testcompanion.R

class CategoryAdapter(private val dataSet: List<String>, private val courseImageList: ArrayList<Int>, private val mainActivity: MainActivity) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.courseName)
        val courseImage: ImageView = view.findViewById(R.id.courseImage)
        val categoryCard: LinearLayout = view.findViewById(R.id.categoryCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataSet[position]
        holder.courseImage.setImageResource(courseImageList[position])
        holder.categoryCard.setOnClickListener {
            mainActivity.goToTestActivity(dataSet[position])
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}