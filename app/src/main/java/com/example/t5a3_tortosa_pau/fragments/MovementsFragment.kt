package com.example.t5a3_tortosa_pau.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t5a3_tortosa_pau.R
import com.example.t5a3_tortosa_pau.adapters.MovementsAdapter
import com.example.t5a3_tortosa_pau.adapters.MovimientoListener
import com.example.t5a3_tortosa_pau.bd.MiBancoOperacional
import com.example.t5a3_tortosa_pau.databinding.FragmentMovementsBinding
import com.example.t5a3_tortosa_pau.pojo.Cuenta
import com.example.t5a3_tortosa_pau.pojo.Movimiento
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat

class MovementsFragment : Fragment(), MovimientoListener {
    private lateinit var movementsAdapter: MovementsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemDecoration: DividerItemDecoration
    private lateinit var binding: FragmentMovementsBinding
    private var movimientos: List<Movimiento> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovementsBinding.inflate(inflater, container, false)

        val cuenta = arguments?.getSerializable("cuentaSeleccionada") as? Cuenta
        cuenta?.let {
            movimientos = getMovimientos(cuenta) as List<Movimiento>
        }

        movementsAdapter = MovementsAdapter(movimientos, object : MovementsAdapter.OnMovementClickListener {
            override fun onMovementClick(movimiento: Movimiento) {
                val dialogo = layoutInflater.inflate(R.layout.movement_dialog, null)

                val txtDetalle = dialogo.findViewById<TextView>(R.id.txtDetalle)

                val formatoFecha = SimpleDateFormat("dd-MM-yyyy")
                val fecha = formatoFecha.format(movimiento.getFechaOperacion())


                txtDetalle.text = "id: " + movimiento.getId() + "\ntipo: " + movimiento.getTipo() + "\nfecha operacion: " + fecha + "\ndescripcion: " + movimiento.getDescripcion()

                context?.let {
                    MaterialAlertDialogBuilder(it)
                        .setTitle("Detalle del movimiento")
                        .setView(dialogo)
                        .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, i ->
                            dialog.cancel()
                        })
                        .setCancelable(false)
                        .show()
                }
            }
        })
        linearLayoutManager = LinearLayoutManager(context)
        itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        binding.recyclerViewM.apply{
            layoutManager = linearLayoutManager
            adapter = movementsAdapter
            addItemDecoration(itemDecoration)
        }

        return binding.root
    }

    fun getMovimientos(cuenta: Cuenta): ArrayList<*>? {
        val bancoOperacional = MiBancoOperacional.getInstance(context)
        val cuentasCliente = bancoOperacional?.getMovimientos(cuenta)
        return cuentasCliente
    }

    companion object {
        @JvmStatic
        fun newInstance(c: Cuenta) =
            MovementsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("cuentaSeleccionada", c)
                }
            }
    }

    override fun onMovimientoSeleccionada(mov: Movimiento) { }
}