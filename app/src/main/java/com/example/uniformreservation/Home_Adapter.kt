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

class HomeAdapter(private val uniforms: List<Uniform>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.univ_logo)
        val nameTextView: TextView = itemView.findViewById(R.id.uniformRSO)
        val sizeTextView: TextView = itemView.findViewById(R.id.uniformSizes)
        val totalAvailableTextView: TextView = itemView.findViewById(R.id.tvTotalAvailable)
        val departmentTextView: TextView = itemView.findViewById(R.id.uniformDepartment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val uniform = uniforms[position]
        holder.nameTextView.text = uniform.name
        holder.sizeTextView.text = "Size: " + uniform.size
        holder.imageView.load(uniform.image_url) // Loads the image URL using Coil
        holder.totalAvailableTextView.text = "Total Available Uniform: " + uniform.available?.toString() ?: "0" // Convert Int? to String
        holder.departmentTextView.text = "Department: " + uniform.department

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ViewUniform::class.java)
            intent.putExtra("image", uniform.image_url)
            intent.putExtra("totalAvailable", uniform.available ?: 0) // Use Int with default value
            intent.putExtra("name", uniform.name)
            intent.putExtra("size", uniform.size)
            intent.putExtra("department", uniform.department)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = uniforms.size
}