package com.example.t5a3_tortosa_pau

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.t5a3_tortosa_pau.databinding.ItemMovementsBinding
import com.example.t5a3_tortosa_pau.pojo.Movimiento
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MovementsAdapter(private val movimientos: List<Movimiento>): RecyclerView.Adapter<MovementsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemMovementsBinding.bind(view)
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
            val fecha = movimiento.getFechaOperacion().toString()
            val date = Date(fecha)
            val fechaFormateada = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val fechaFinal = fechaFormateada.format(date)

            binding.txtMovementName.text = movimiento.getDescripcion()
            binding.txtMovementInfo.text = fechaFinal + " Importe: " + movimiento.getImporte()
            if (movimiento.getImporte() > 0) {
                binding.txtMovementInfo.resources.getColor(R.color.green)
            }
            binding.imgMovement.setImageResource(R.drawable.cerdito)
        }
    }

    override fun getItemCount(): Int = movimientos.size

}