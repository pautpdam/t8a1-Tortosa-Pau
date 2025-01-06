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
    private var cuenta: Cuenta? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovementsBinding.inflate(inflater, container, false)

        cuenta = arguments?.getSerializable("cuentaSeleccionada") as? Cuenta

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_no_type -> refreshMovements(-1)
                R.id.item_type_0 -> refreshMovements(0)
                R.id.item_type_1 -> refreshMovements(1)
                R.id.item_type_2 -> refreshMovements(2)
            }
            true
        }

        val tipo = arguments?.getSerializable("tipo") as? Int ?: -1

        movimientos = cuenta?.let { getMovimientos(it, tipo) } as List<Movimiento>

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

    private fun refreshMovements(tipo: Int) {
        cuenta?.let {
            movimientos = getMovimientos(it, tipo) as List<Movimiento>
            movementsAdapter.updateMovimientos(movimientos)
        }
    }

    fun getMovimientos(cuenta: Cuenta, tipo: Int): ArrayList<*>? {
        val bancoOperacional = MiBancoOperacional.getInstance(context)
        val movimientosCliente: ArrayList<*>?
        if (tipo in 0..2) {
            movimientosCliente = bancoOperacional?.getMovimientosTipo(cuenta, tipo)
        } else {
            movimientosCliente = bancoOperacional?.getMovimientos(cuenta)
        }
        return movimientosCliente
    }

    companion object {
        @JvmStatic
        fun newInstance(c: Cuenta, tipo: Int) =
            MovementsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("cuentaSeleccionada", c)
                    putSerializable("tipo", tipo)
                }
            }
    }

    override fun onMovimientoSeleccionada(mov: Movimiento) { }

    private fun MovementsAdapter.updateMovimientos(newMovimientos: List<Movimiento>) {
        this.movimientos = newMovimientos
        this.notifyDataSetChanged()
    }

}