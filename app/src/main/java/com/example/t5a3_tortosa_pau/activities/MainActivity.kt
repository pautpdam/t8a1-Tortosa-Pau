package com.example.t5a3_tortosa_pau.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.t5a3_tortosa_pau.R
import com.example.t5a3_tortosa_pau.databinding.ActivityMainBinding
import com.example.t5a3_tortosa_pau.pojo.Cliente
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cliente = intent.getSerializableExtra("Cliente") as Cliente
        val nombreCliente = cliente.getNombre()
        val apellidosCliente = cliente.getApellidos()
        binding.textoBienvenida.text = binding.textoBienvenida.text.toString() + nombreCliente + " " + apellidosCliente

        binding.btnChangePassword.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        binding.btnGlobalPosition.setOnClickListener {
            val intent = Intent(this, GlobalPositionActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        binding.btnMovements.setOnClickListener {
            val intent = Intent(this, MovementsActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        binding.btnTransfers.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        binding.btnExit.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        drawerLayout = findViewById<DrawerLayout>(R.id.main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.drawerArrowDrawable.color = resources.getColor(android.R.color.white, theme)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val cliente = intent.getSerializableExtra("Cliente") as Cliente

        when(item.itemId) {
            R.id.item_home -> {
                // Ya estamos en la MainActivity
            }
            R.id.item_global_position -> {
                val intent = Intent(this, GlobalPositionActivity::class.java)
                intent.putExtra("Cliente", cliente)
                startActivity(intent)
            }
            R.id.item_movements -> {
                val intent = Intent(this, MovementsActivity::class.java)
                intent.putExtra("Cliente", cliente)
                startActivity(intent)
            }
            R.id.item_transfers -> {
                val intent = Intent(this, TransferActivity::class.java)
                intent.putExtra("Cliente", cliente)
                startActivity(intent)
            }
            R.id.item_change_password -> {
                val intent = Intent(this, ChangePasswordActivity::class.java)
                intent.putExtra("Cliente", cliente)
                startActivity(intent)
            }
            R.id.item_promotions -> {

            }
            R.id.item_cashiers -> {

            }
            R.id.item_configuration -> {

            }
            R.id.item_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}