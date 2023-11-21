package com.example.denikv1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CestaAdapter(private val cesta: List<CestaEntity>) : RecyclerView.Adapter<CestaAdapter.CestaViewHolder>() {

    class CestaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val CestaName: TextView = view.findViewById(R.id.cestaName)
        val CestaGrade: TextView = view.findViewById(R.id.cestaGrade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CestaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cesta, parent, false)
        return CestaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CestaViewHolder, position: Int) {
        val cesta = cesta[position]
        holder.CestaName.text = cesta.roadName
        holder.CestaGrade.text = cesta.grade

        val spacingInPixels = holder.itemView.resources.getDimensionPixelSize(R.dimen.spacing_between_items)
        val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(spacingInPixels, spacingInPixels, spacingInPixels, spacingInPixels)
        holder.itemView.layoutParams = layoutParams
    }

    override fun getItemCount() = cesta.size
}