package com.example.asistenciadevacas

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class ListaControles : AppCompatActivity() {

    private var vaca: VacaModel? = null  // Guardamos la vaca seleccionada

    companion object {
        var controlAdapter: ControlAdapter? = null
        var controles: MutableList<ControlModel> = mutableListOf()
        var controlesFiltradas: MutableList<ControlModel> = mutableListOf()
        var isLoading = false
        var isLastPage = false
        var currentPage = 0
        const val pageSize = 10 // Número de controles por página
        val REQUEST_CODE_ADD_VACA = 1
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_de_controles)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val listaControles = findViewById<RecyclerView>(R.id.rvListaControles)
        val layoutManager = LinearLayoutManager(this)
        listaControles.layoutManager = layoutManager

        controlAdapter = ControlAdapter(controlesFiltradas)
        listaControles.adapter = controlAdapter

        // Obtener la vaca desde el Intent una sola vez
        vaca = intent.getParcelableExtra("vaca")

        // Cargar la primera página de datos
        loadControles(vaca?.id_vaca)

        // Configurar el EditText para la búsqueda
        val etBuscarcontrol = findViewById<EditText>(R.id.etBuscarControl)
        etBuscarcontrol.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val texto = s.toString()
                controlAdapter?.filtrar(texto)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Configurar el scroll listener para paginación
        listaControles.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage++
                loadControles(vaca?.id_vaca)  // Usamos el vaca_id guardado
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })

        // Configuración del botón para añadir un control
        val btnAniadirVaca = findViewById<Button>(R.id.btnAniadirControl)
        btnAniadirVaca.setOnClickListener {
            // Pasar el ID de la vaca actual al siguiente intent
            val intent = Intent(this, ControlVaca::class.java)
            intent.putExtra("vaca_id", vaca?.id_vaca)  // Pasar el vaca_id al siguiente Activity
            startActivityForResult(intent, REQUEST_CODE_ADD_VACA)
        }
    }

    private fun loadControles(vaca_id: Int?) {

        // Limpiar las listas antes de cargar nuevos datos
        val conexion = ConexionDB(this)
        val nuevasControles = conexion.getControlesPaginadas(currentPage * pageSize, pageSize, vaca_id)

        Log.d("Lista De Controles", "Nuevos Controles: ${nuevasControles.size}")

        if (nuevasControles.isNotEmpty()) {
            controles.clear()
            controlesFiltradas.clear()
            controles.addAll(nuevasControles)
            controlesFiltradas.addAll(nuevasControles)
            controlAdapter?.notifyDataSetChanged()
        } else {
            isLastPage = true
        }

        isLoading = false
    }
}