package com.example.t5a3_tortosa_pau.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t5a3_tortosa_pau.R
import com.example.t5a3_tortosa_pau.adapters.CuentasListener
import com.example.t5a3_tortosa_pau.adapters.GlobalPositionAdapter
import com.example.t5a3_tortosa_pau.bd.MiBancoOperacional
import com.example.t5a3_tortosa_pau.databinding.ActivityGlobalPositionBinding
import com.example.t5a3_tortosa_pau.fragments.MovementsFragment
import com.example.t5a3_tortosa_pau.pojo.Cliente
import com.example.t5a3_tortosa_pau.pojo.Cuenta

class GlobalPositionActivity : AppCompatActivity(), CuentasListener {

    private lateinit var binding: ActivityGlobalPositionBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemDecoration: DividerItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlobalPositionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        linearLayoutManager = LinearLayoutManager(this)
        itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        val bancoOperacional = MiBancoOperacional.getInstance(this)
        val cliente = intent.getSerializableExtra("Cliente") as? Cliente
        if (cliente == null) {
            finish()
            return
        }
        val cuentasCliente = bancoOperacional?.getCuentas(cliente)
        if (cuentasCliente.isNullOrEmpty()) {
            return
        }
    //    cuentasCliente.setCuentasListener(this)
    }

    override fun onCuentaSeleccionada(cuenta: Cuenta) {
        if (cuenta != null) {
            var hayCuenta = supportFragmentManager.findFragmentById(R.id.fragmentMovimientos) != null

            val movimientoFragment = MovementsFragment()

            val bundle = Bundle()
            bundle.putSerializable("cuentaSeleccionada", cuenta)
            movimientoFragment.arguments = bundle

            if (hayCuenta) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentMovimientos, movimientoFragment)
                transaction.commit()
            } else {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentPosicionesGlobales, movimientoFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }
}

/*
private lateinit var globalPositionAdapter: GlobalPositionAdapter

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityGlobalPositionBinding.inflate(layoutInflater)
    setContentView(binding.root)

    linearLayoutManager = LinearLayoutManager(this)
    itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

    binding.recViewCuentas.apply {
        layoutManager = linearLayoutManager
        addItemDecoration(itemDecoration)
    }

    val bancoOperacional = MiBancoOperacional.getInstance(this)
    val cliente = intent.getSerializableExtra("Cliente") as? Cliente
    if (cliente == null) {
        finish()
        return
    }
    val cuentasCliente = bancoOperacional?.getCuentas(cliente)
    if (cuentasCliente.isNullOrEmpty()) {
        return
    }
    globalPositionAdapter = GlobalPositionAdapter(cuentasCliente as List<Cuenta>)
    binding.recViewCuentas.adapter = globalPositionAdapter
}
*/