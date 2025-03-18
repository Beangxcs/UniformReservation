package com.example.uniformreservation

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.uniformreservation.model.Uniform
import com.example.uniformreservation.R

class HomeAdapter(private val lists: List<Uniform>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.univ_logo)
        val nameTextView: TextView = itemView.findViewById(R.id.uniformRSO)
        val sizeTextView: TextView = itemView.findViewById(R.id.uniformSizes)
        val categoryTextView: TextView = itemView.findViewById(R.id.tvcategory)
        val departmentTextView: TextView = itemView.findViewById(R.id.uniformDepartment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lists[position]
        holder.nameTextView.text = item.name
        holder.sizeTextView.text = item.size
        holder.imageView.load(item.image_url)
        holder.categoryTextView.text = item.category
        holder.departmentTextView.text = item.department

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ViewUniform::class.java)
            intent.putExtra("image",item.image_url)
            intent.putExtra("category", item.category)
            intent.putExtra("name", item.name)
            intent.putExtra("size", item.size)
            intent.putExtra("department", item.department)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = lists.size
}











