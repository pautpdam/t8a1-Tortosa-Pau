package com.example.t5a3_tortosa_pau

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.t5a3_tortosa_pau.bd.MiBancoOperacional
import com.example.t5a3_tortosa_pau.databinding.ActivityTransferBinding
import com.example.t5a3_tortosa_pau.pojo.Cliente
import com.example.t5a3_tortosa_pau.pojo.Cuenta
import com.google.android.material.button.MaterialButton
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.textfield.TextInputEditText

class TransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val cliente = intent.getSerializableExtra("Cliente") as Cliente

        val spCuentasEnviar: Spinner = findViewById<Spinner>(R.id.spCuentaEnviar)
        val spCuentasRecibir: Spinner = findViewById<Spinner>(R.id.spCuentaRecibir)
        val spDivisas: Spinner = findViewById<Spinner>(R.id.spDivisas)

        val bancoOperacional = MiBancoOperacional.getInstance(this)
        val cuentasCliente: ArrayList<*>? = bancoOperacional?.getCuentas(cliente)
        val numeroCuentas = ArrayList<String>()

        cuentasCliente?.forEach { cuenta ->
            if (cuenta is Cuenta) {
                numeroCuentas.add(cuenta.getNumeroCuenta() ?: "")
            }
        }

        val adapterCuentas = ArrayAdapter(this, android.R.layout.simple_spinner_item, numeroCuentas)
        val adapterDivisas = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.divisas))

        adapterCuentas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterDivisas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spCuentasEnviar.adapter = adapterCuentas
        spCuentasRecibir.adapter = adapterCuentas
        spDivisas.adapter = adapterDivisas


        val btnCuentaPropia: MaterialRadioButton = findViewById<MaterialRadioButton>(R.id.btnCuentaPropia)
        val btnCuentaAjena: MaterialRadioButton = findViewById<MaterialRadioButton>(R.id.btnCuentaAjena)
        val txtInputCuentaAjena: TextInputEditText = findViewById<TextInputEditText>(R.id.txtInputCuentaAjena)

        btnCuentaPropia.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                spCuentasRecibir.visibility = View.VISIBLE
                txtInputCuentaAjena.visibility = View.GONE
            }
        }

        btnCuentaAjena.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                spCuentasRecibir.visibility = View.GONE
                txtInputCuentaAjena.visibility = View.VISIBLE
            }
        }


        val cbJustificante: CheckBox = findViewById<CheckBox>(R.id.cbJustificante)

        binding.btnEnviarTransferencia.setOnClickListener {
            var cuentaRecibir: String
            var tipoCuenta: String
            var enviarJustificante: String = ""

            if (cbJustificante.isChecked) {
                if (binding.txtCantidad.text == "Amount:") {
                    enviarJustificante = "\nSend justification"
                } else {
                    enviarJustificante = "\nEnviar justificante"
                }
            }

            if (btnCuentaPropia.isChecked) {
                cuentaRecibir = spCuentasRecibir.selectedItem.toString()
                if (binding.txtCantidad.text == "Amount:") {
                    tipoCuenta = "your own"
                } else {
                    tipoCuenta = "propia"
                }
            } else {
                cuentaRecibir = txtInputCuentaAjena.text.toString()
                if (binding.txtCantidad.text == "Amount:") {
                    tipoCuenta = "someone\'s"
                } else {
                    tipoCuenta = "ajena"
                }
            }

            if (binding.txtCantidad.text == "Amount:") {
                Toast.makeText(this, "Cuenta origen:\n" +
                        spCuentasEnviar.selectedItem.toString() + "\n" +
                        "A cuenta " + tipoCuenta + ":\n" +
                        cuentaRecibir + "\n" +
                        "Importe: " + binding.txtInputCantidad.text.toString() + spDivisas.selectedItem.toString() +
                        enviarJustificante, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Source account:\n" +
                        spCuentasEnviar.selectedItem.toString() + "\n" +
                        "To " + tipoCuenta + "account:\n" +
                        cuentaRecibir + "\n" +
                        "Amount: " + binding.txtInputCantidad.text.toString() + spDivisas.selectedItem.toString() +
                        enviarJustificante, Toast.LENGTH_LONG).show()
            }
        }

        binding.btnCancelarTransferencia.setOnClickListener {
            spCuentasEnviar.setSelection(0)
            spCuentasRecibir.setSelection(0)
            txtInputCuentaAjena.setText("")
            binding.txtInputCantidad.setText("")
            spDivisas.setSelection(0)
            cbJustificante.isChecked = false
        }


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}