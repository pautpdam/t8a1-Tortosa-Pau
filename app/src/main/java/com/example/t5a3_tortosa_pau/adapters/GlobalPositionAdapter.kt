package com.example.t5a3_tortosa_pau.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.t5a3_tortosa_pau.R
import com.example.t5a3_tortosa_pau.databinding.ItemCuentasBinding
import com.example.t5a3_tortosa_pau.pojo.Cuenta

class GlobalPositionAdapter(private val cuentas: List<Cuenta>): RecyclerView.Adapter<GlobalPositionAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemCuentasBinding.bind(view)
    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_cuentas, parent, false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cuenta = cuentas.get(position)
        with(holder) {
            binding.txtNumeroCuenta.text = cuenta.getBanco() + "-" + cuenta.getSucursal() + "-" + cuenta.getDc() + "-" + cuenta.getNumeroCuenta()
            binding.txtSaldo.text = cuenta.getSaldoActual().toString()
            binding.txtSaldo.setTextColor(
                if (cuenta.getSaldoActual()!! > 0) {
                    ContextCompat.getColor(context, R.color.green)
                } else {
                    ContextCompat.getColor(context, R.color.red)
                }
            )
        }
    }

    override fun getItemCount(): Int = cuentas.size

}