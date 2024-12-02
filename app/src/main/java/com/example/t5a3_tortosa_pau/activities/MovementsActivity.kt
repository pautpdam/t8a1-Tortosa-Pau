package com.example.t5a3_tortosa_pau.activities

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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

class MovementsActivity : AppCompatActivity() {

    private lateinit var movementsAdapter: MovementsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemDecoration: DividerItemDecoration
    private lateinit var binding: ActivityMovementsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spCuentaSeleccionada: Spinner = findViewById(R.id.spCuentaSeleccionada)
        val cliente = intent.getSerializableExtra("Cliente") as? Cliente
        val bancoOperacional = MiBancoOperacional.getInstance(this)
        val cuentasCliente: ArrayList<*>? = bancoOperacional?.getCuentas(cliente)
        val numeroCuentas = ArrayList<String>()

        cuentasCliente?.forEach { cuenta ->
            if (cuenta is Cuenta) {
                numeroCuentas.add(
                    "${cuenta.getBanco()}-${cuenta.getSucursal()}-${cuenta.getDc()}-${cuenta.getNumeroCuenta()}"
                )
            }
        }

        val adapterCuentas = ArrayAdapter(this, android.R.layout.simple_spinner_item, numeroCuentas)
        adapterCuentas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spCuentaSeleccionada.adapter = adapterCuentas

        linearLayoutManager = LinearLayoutManager(this)
        itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        binding.recViewMovimientos.apply {
            layoutManager = linearLayoutManager
            addItemDecoration(itemDecoration)
        }

        spCuentaSeleccionada.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val cuentaSeleccionada = cuentasCliente?.get(position) as? Cuenta
                val movimientos = bancoOperacional?.getMovimientos(cuentaSeleccionada) as List<Movimiento>

                movementsAdapter = MovementsAdapter(movimientos)
                binding.recViewMovimientos.adapter = movementsAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>) { }
        }

        if (cuentasCliente != null && cuentasCliente.isNotEmpty()) {
            val cuentaInicial = cuentasCliente[0] as? Cuenta
            val movimientosIniciales = bancoOperacional.getMovimientos(cuentaInicial) as List<Movimiento>

            movementsAdapter = MovementsAdapter(movimientosIniciales)
            binding.recViewMovimientos.adapter = movementsAdapter
        }
    }
}