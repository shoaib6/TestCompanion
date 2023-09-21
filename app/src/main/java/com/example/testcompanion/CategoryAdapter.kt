package com.example.testcompanion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(private val dataSet: List<String>, private val courseImageList: ArrayList<Int>, private val categoriesFragment: CategoriesFragment) :
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
            categoriesFragment.goToTestActivity(dataSet[position])
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}