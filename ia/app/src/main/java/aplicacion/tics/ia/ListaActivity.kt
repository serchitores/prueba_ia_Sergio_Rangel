package aplicacion.tics.ia

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_lista.*

class ListaActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)


        var db = DataBaseHandler(this)
        val data = db.readData()
        if (data.count() == 0){
            Toast.makeText(this, "aun no hay registros porfavor ingresa un nuevo usuario", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "cargando datos", Toast.LENGTH_LONG).show()
        }
        setUpRecyclerView()

        button_add.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)        }

    }


    @SuppressLint("WrongConstant")
    fun setUpRecyclerView() {

        val db = DataBaseHandler(this)
        val UserList: ArrayList<User> = db.readData()
        val adapter = AdapterUser(this, UserList)
        val rv: RecyclerView = findViewById(R.id.recycler_user)
        rv.layoutManager =
            LinearLayoutManager(this, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager
        rv.adapter = adapter
    }
}

