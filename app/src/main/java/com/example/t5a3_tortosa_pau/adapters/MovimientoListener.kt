package com.example.t5a3_tortosa_pau.adapters

import com.example.t5a3_tortosa_pau.pojo.Cuenta
import com.example.t5a3_tortosa_pau.pojo.Movimiento

interface MovimientoListener {
    fun onMovimientoSeleccionada(mov: Movimiento)

}