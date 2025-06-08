package com.example.proyectorecu

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectorecu.adapters.TareaAdapter
import com.example.proyectorecu.databinding.ActivityMainBinding
import com.example.proyectorecu.fragments.EstadisticasFragment
import com.example.proyectorecu.models.TareaModel
import com.example.proyectorecu.providers.db.CrudTareas

class MainActivity : AppCompatActivity() {

    // Binding para acceder a los elementos de la interfaz sin usar findViewById
    private lateinit var binding: ActivityMainBinding

    // Lista mutable que almacena las tareas
    var lista= mutableListOf<TareaModel>()

    // Adaptador para el RecyclerView
    private lateinit var adapter: TareaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //applicationContext.deleteDatabase

        //setSearchView()
        setListeners()
        setRecycler()
        val estadisticas = EstadisticasFragment()
       // cargarFraments(estadisticas)
        title="Mis Tareas"
    }

    private fun setRecycler() {

        // Configura el RecyclerView con un LinearLayoutManager
        val layoutManger=LinearLayoutManager(this)
        binding.recyclerView.layoutManager=layoutManger
        traerRegistros()
        adapter=TareaAdapter(lista, { position->borrarContacto(position)}, { c->update(c)})
        binding.recyclerView.adapter=adapter
    }
    //----------------------------------------------------------------------------------------------

    // Método para actualizar una tarea, abre la actividad de edición con la tarea seleccionada
    private fun update(t: TareaModel){
        val i=Intent(this, AddActivity::class.java).apply {
            putExtra("TAREA", t)
        }
        startActivity(i)
    }
    //----------------------------------------------------------------------------------------------

    // Método para eliminar una tarea de la lista y de la base de datos
    private fun borrarContacto(p: Int){
        val id=lista[p].id
        //Lo elimino de la lisa
        lista.removeAt(p)
        //lo elimino de la base de datos
        if(CrudTareas().borrar(id)){
            adapter.notifyItemRemoved(p)
        }else{
            Toast.makeText(this, "No se eliminó ningún registro", Toast.LENGTH_SHORT).show()
        }
    }
    //----------------------------------------------------------------------------------------------

    // Método para obtener las tareas de la base de datos
    private fun traerRegistros() {
        lista=CrudTareas().read()
        if(lista.size>0){
            binding.ivTareas.visibility=View.INVISIBLE
        }else{
            binding.ivTareas.visibility=View.VISIBLE
        }

    }

    // Configura el botón flotante para agregar nuevas tareas
    private fun setListeners() {
        binding.fabAdd.setOnClickListener{
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    // Se ejecuta cuando la actividad vuelve a primer plano
    override fun onRestart() {
        super.onRestart()
        setRecycler()
    }
    /////////////////////Menu principal
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Maneja las opciones del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_salir->{
                finish()
            }
            R.id.item_borrar_todo->{
                confirmarBorrado()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    // Muestra un diálogo de confirmación para borrar todas las tareas
    private fun confirmarBorrado(){
        val builder=AlertDialog.Builder(this)
            .setTitle("¿Borrar Tareas?")
            .setMessage("¿Borrar todas las tareas?")
            .setNegativeButton("CANCELAR"){
                    dialog,_->dialog.dismiss()
            }
            .setPositiveButton("ACEPTAR"){
                    _,_->
                CrudTareas().borrarTodo()
                setRecycler()
            }
            .create()
            .show()
    }

    // Configura el SearchView para filtrar las tareas
    /*private fun setSearchView(){
        // Configuración del SearchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Se ejecuta cuando el usuario presiona "Buscar"
                query?.let {
                    if (query == ""){
                        lista = CrudTareas().read()
                    }else{
                        lista = CrudTareas().searchItems(query)
                    }
                    adapter=TareaAdapter(lista, { position->borrarContacto(position)}, { c->update(c)})
                    binding.recyclerView.adapter=adapter
                }
                return true
            }*/

            /*override fun onQueryTextChange(newText: String?): Boolean {
                // Filtra la lista en tiempo real mientras el usuario escribe
                newText?.let {
                    if (newText == ""){
                        lista = CrudTareas().read()
                    }else{
                        lista = CrudTareas().searchItems(newText)
                    }
                    adapter=TareaAdapter(lista, { position->borrarContacto(position)}, { c->update(c)})
                    binding.recyclerView.adapter=adapter
                }
                return true
            }
        })
    }

    // Método para cargar el fragmento de estadísticas
    private fun cargarFraments(estadisticas: EstadisticasFragment){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fcv_estadisticas, estadisticas)
        }
    }*/
}