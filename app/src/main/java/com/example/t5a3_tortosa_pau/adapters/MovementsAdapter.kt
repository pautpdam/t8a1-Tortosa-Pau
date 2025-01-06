package com.example.t5a3_tortosa_pau.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.t5a3_tortosa_pau.R
import com.example.t5a3_tortosa_pau.databinding.ItemMovementsBinding
import com.example.t5a3_tortosa_pau.pojo.Movimiento

class MovementsAdapter(
    var movimientos: List<Movimiento>,
    private val listener: OnMovementClickListener
) : RecyclerView.Adapter<MovementsAdapter.ViewHolder>() {

    interface OnMovementClickListener {
        fun onMovementClick(movimiento: Movimiento)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMovementsBinding.bind(view)

        init {
            itemView.setOnClickListener {
                val movimiento = movimientos[adapterPosition]
                listener.onMovementClick(movimiento)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movements, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movimientos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movimiento = movimientos[position]
        with(holder.binding) {
            txtMovementName.text = movimiento.getDescripcion()
            txtMovementInfo.text = movimiento.getImporte().toString()
        }
    }
}