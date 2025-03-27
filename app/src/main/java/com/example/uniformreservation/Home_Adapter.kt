package com.example.uniformreservation

import android.content.Intent
import android.util.Log
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
        val imageView: ImageView = itemView.findViewById(R.id.citelogo)
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

        // Use the correct image URL from the API
        val imageUrl = uniform.image_url

        // Debugging: Print URL in Logcat
        Log.d("HomeAdapter", "Full Image URL: $imageUrl")

        // Set text values
        holder.nameTextView.text = uniform.name
        holder.sizeTextView.text = "Size: ${uniform.size}"
        holder.totalAvailableTextView.text = "Total Uniform: ${uniform.available ?: 0}"
        holder.departmentTextView.text = "Department: ${uniform.department}"

        // Load image with Coil
        holder.imageView.load(imageUrl) {
            placeholder(R.drawable.citelogo) // Change this to an actual drawable
            error(R.drawable.citelogo) // Change this to an actual error drawable
        }

        // Open ViewUniform activity on item click
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ViewUniform::class.java).apply {
                putExtra("image", imageUrl) // Pass full URL
                putExtra("totalAvailable", uniform.available ?: 0)
                putExtra("name", uniform.name)
                putExtra("size", uniform.size)
                putExtra("department", uniform.department)
            }
            it.context.startActivity(intent)
        }
    }



    override fun getItemCount(): Int = uniforms.size
}