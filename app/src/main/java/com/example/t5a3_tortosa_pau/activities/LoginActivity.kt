package com.example.t5a3_tortosa_pau.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.t5a3_tortosa_pau.R
import com.example.t5a3_tortosa_pau.bd.MiBancoOperacional
import com.example.t5a3_tortosa_pau.databinding.ActivityLoginBinding
import com.example.t5a3_tortosa_pau.pojo.Cliente

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEntrar.setOnClickListener {
            val usuario = binding.editUsuario.text.toString()
            val contrasena = binding.editContrasena.text.toString()
            val comprobador = binding.textoInicioSesion.text

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                if (comprobador.equals("Log in")) {
                    binding.textoError.text = "Error: You must enter all the parameters."
                } else {
                    binding.textoError.text = "Error: Debe introducir todos los parámetros."
                }
            } else {
                val bancoOperacional: MiBancoOperacional? = MiBancoOperacional.getInstance(this)

                var cliente = Cliente()
                cliente.setNif(usuario)
                cliente.setClaveSeguridad(contrasena)

                val clienteLogeado = bancoOperacional?.login(cliente) ?: -1

                if(clienteLogeado == -1) {
                    Toast.makeText(this, "El cliente y/o contraseña no existen.", Toast.LENGTH_LONG).show()
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("Cliente", clienteLogeado)
                    startActivity(intent)
                }
            }
        }

        binding.btnSalir.setOnClickListener {
            finishAffinity()
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