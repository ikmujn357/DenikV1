package com.example.denikv1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CestaAdapter(private val cesta: List<Cesta>) : RecyclerView.Adapter<CestaAdapter.CestaViewHolder>() {

    class CestaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val CestaTitle: TextView = view.findViewById(R.id.cestaTitle)
        val CestaDescription: TextView = view.findViewById(R.id.cestaDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CestaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cesta, parent, false)
        return CestaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CestaViewHolder, position: Int) {
        val cesta = cesta[position]
        holder.CestaTitle.text = cesta.title
        holder.CestaDescription.text = cesta.description

        val spacingInPixels = holder.itemView.resources.getDimensionPixelSize(R.dimen.spacing_between_items)
        val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(spacingInPixels, spacingInPixels, spacingInPixels, spacingInPixels)
        holder.itemView.layoutParams = layoutParams
    }

    override fun getItemCount() = cesta.size
}