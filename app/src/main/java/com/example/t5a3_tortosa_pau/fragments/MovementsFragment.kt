package com.example.t5a3_tortosa_pau.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t5a3_tortosa_pau.R
import com.example.t5a3_tortosa_pau.adapters.MovementsAdapter
import com.example.t5a3_tortosa_pau.bd.MiBancoOperacional
import com.example.t5a3_tortosa_pau.databinding.FragmentMovementsBinding
import com.example.t5a3_tortosa_pau.pojo.Cuenta
import com.example.t5a3_tortosa_pau.pojo.Movimiento

private const val CUENTA = "cuenta"

class MovementsFragment : Fragment() {
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

        movementsAdapter = MovementsAdapter(movimientos)
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
                    putSerializable(CUENTA, c)
                }
            }
    }
}