package com.example.t5a3_tortosa_pau.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.t5a3_tortosa_pau.R
import com.example.t5a3_tortosa_pau.bd.MiBancoOperacional
import com.example.t5a3_tortosa_pau.databinding.ActivityChangePasswordBinding
import com.example.t5a3_tortosa_pau.pojo.Cliente

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCambiar.setOnClickListener {
            var contrasenaActual: String = binding.editContrasenaActual.text.toString()
            var contrasenaNueva: String = binding.editContrasenaNueva.text.toString()

            val cliente = intent.getSerializableExtra("Cliente") as Cliente
            val bancoOperacional = MiBancoOperacional.getInstance(this)

            if (cliente.getClaveSeguridad().equals(contrasenaActual)) {
                cliente.setClaveSeguridad(contrasenaNueva)
                bancoOperacional?.changePassword(cliente)
                Toast.makeText(this, "Contraseña cambiada correctamente", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "La contraseña actual no concuerda", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnCancelar.setOnClickListener {
            binding.editContrasenaActual.setText("")
            binding.editContrasenaNueva.setText("")
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