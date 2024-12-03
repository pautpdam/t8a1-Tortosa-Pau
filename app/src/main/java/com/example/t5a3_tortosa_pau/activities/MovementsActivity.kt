package com.example.t5a3_tortosa_pau.activities

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t5a3_tortosa_pau.R
import com.example.t5a3_tortosa_pau.adapters.MovementsAdapter
import com.example.t5a3_tortosa_pau.bd.MiBancoOperacional
import com.example.t5a3_tortosa_pau.databinding.ActivityMovementsBinding
import com.example.t5a3_tortosa_pau.pojo.Cliente
import com.example.t5a3_tortosa_pau.pojo.Cuenta
import com.example.t5a3_tortosa_pau.pojo.Movimiento

class MovementsActivity : AppCompatActivity(), MovementsAdapter.OnMovementClickListener {

    private lateinit var binding: ActivityMovementsBinding
    private lateinit var movimientoAdapter: MovementsAdapter
    private lateinit var cuentasCliente: List<Cuenta>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cliente = intent.getSerializableExtra("Cliente") as? Cliente
        val bancoOperacional = MiBancoOperacional.getInstance(this)

        cuentasCliente = bancoOperacional?.getCuentas(cliente) as? List<Cuenta> ?: emptyList()

        val cuentas = cuentasCliente.map {
            "${it.getBanco()}-${it.getSucursal()}-${it.getDc()}-${it.getNumeroCuenta()}"
        }
        val adapterCuentas = ArrayAdapter(this, android.R.layout.simple_spinner_item, cuentas)
        adapterCuentas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCuentaSeleccionada.adapter = adapterCuentas

        binding.recViewMovimientos.layoutManager = LinearLayoutManager(this)
        binding.recViewMovimientos.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        binding.spCuentaSeleccionada.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val cuentaSeleccionada = cuentasCliente[position]
                actualizarMovimientos(cuentaSeleccionada)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        if (cuentasCliente.isNotEmpty()) {
            actualizarMovimientos(cuentasCliente[0])
        }
    }

    private fun actualizarMovimientos(cuenta: Cuenta) {
        val bancoOperacional = MiBancoOperacional.getInstance(this)
        val movimientos = bancoOperacional?.getMovimientos(cuenta) ?: emptyList()

        movimientoAdapter = MovementsAdapter(movimientos as List<Movimiento>, this)
        binding.recViewMovimientos.adapter = movimientoAdapter
    }

    override fun onMovementClick(movimiento: Movimiento) { }
}