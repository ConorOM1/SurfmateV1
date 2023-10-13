package org.wit.surfmate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.surfmate.databinding.CardSurfspotBinding
import org.wit.surfmate.models.SurfspotModel

class `SurfmateAdapter` constructor(private var surfspots: List<SurfspotModel>) :
    RecyclerView.Adapter<`SurfmateAdapter`.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardSurfspotBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val surfspot = surfspots[holder.adapterPosition]
        holder.bind(surfspot)
    }

    override fun getItemCount(): Int = surfspots.size

    class MainHolder(private val binding : CardSurfspotBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(surfspot: SurfspotModel) {
            binding.surfspotTitle.text = surfspot.title
            binding.description.text = surfspot.description
        }
    }
}
