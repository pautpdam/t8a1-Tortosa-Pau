package com.example.t5a3_tortosa_pau.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.t5a3_tortosa_pau.R
import com.example.t5a3_tortosa_pau.databinding.ItemMovementsBinding
import com.example.t5a3_tortosa_pau.pojo.Movimiento
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MovementsAdapter(private val movimientos: List<Movimiento>, private val listener: MovimientoListener): RecyclerView.Adapter<MovementsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemMovementsBinding.bind(view)

        fun setListener(mov: Movimiento) {
            binding.root.setOnClickListener {
                listener.onMovimientoSeleccionada(mov)
            }
        }
    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_movements, parent, false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movimiento = movimientos.get(position)

        with(holder) {
            setListener(movimiento)

            binding.txtMovementName.text = movimiento.getDescripcion()
        //    binding.txtMovementInfo.text = movimiento.getFechaOperacion() + movimiento.getImporte()
        }
    }

    override fun getItemCount(): Int = movimientos.size
}