package com.example.uniformreservation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uniformreservation.model.Uniform
import com.example.uniformreservation.R
import org.w3c.dom.Text

class HomeAdapter(private val lists: List<Uniform>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    // ViewHolder class
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.univ_logo)
        val nameTextView: TextView = itemView.findViewById(R.id.uniformRSO)
        val descriptionTextView: TextView = itemView.findViewById(R.id.uniformDescription)
        val sizeTextView: TextView =  itemView.findViewById(R.id.uniformSizes)
        val availabilityTextView: TextView = itemView.findViewById(R.id.uniformAvailability)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lists[position]
        holder.nameTextView.text = item.name
        holder.descriptionTextView.text = item.description
        holder.sizeTextView.text = item.size
        holder.availabilityTextView.text = item.status
        holder.imageView.setImageResource(item.pictures)  // Assuming Uniform has imageResId
    }

    override fun getItemCount(): Int = lists.size
}
