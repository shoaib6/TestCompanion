package com.example.testcompanion

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CategoriesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        val categoryName = ArrayList<String>()
        val courseImageList = ArrayList<Int>()
        courseImageList.add(R.drawable.gat_icon)
        courseImageList.add(R.drawable.nat_icon)
        courseImageList.add(R.drawable.ppsc_icon)
        courseImageList.add(R.drawable.mdcat_icon)
        courseImageList.add(R.drawable.psa_icon)
        categoryName.add("GAT")
        categoryName.add("NTS")
        categoryName.add("PPSC")
        categoryName.add("MDCAT")
        categoryName.add("PSA")
        val adapter = CategoryAdapter(categoryName,courseImageList,this)
        recyclerView.adapter = adapter
        return view
    }

    fun goToTestActivity(categoryName: String) {
        Constant.Category = categoryName
        val intent = Intent(requireContext(),SubjectsActivity::class.java)
        startActivity(intent)
    }

}