package org.wit.surfmate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.surfmate.databinding.CardSurfspotBinding
import org.wit.surfmate.models.SurfspotModel

interface SurfmateListener {
    fun onSurfspotClick(surfspot: SurfspotModel, position : Int)
}

class `SurfmateAdapter` constructor(private var surfspots: List<SurfspotModel>,
                                    private val listener: SurfmateListener) :
    RecyclerView.Adapter<`SurfmateAdapter`.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardSurfspotBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val surfspot = surfspots[holder.adapterPosition]
        holder.bind(surfspot, listener)
    }

    override fun getItemCount(): Int = surfspots.size

    class MainHolder(private val binding : CardSurfspotBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(surfspot: SurfspotModel, listener:SurfmateListener) {
            binding.surfspotTitle.text = surfspot.name
            binding.description.text = surfspot.observations
            binding.surfSpotRating.rating = surfspot.rating
            Picasso.get().load(surfspot.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onSurfspotClick(surfspot, adapterPosition) }
        }
    }
}
