package aplicacion.tics.ia

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import aplicacion.tics.ia.Utils.Utils
import kotlinx.android.synthetic.main.item_user.view.*

class AdapterUser(context: Context, val users: ArrayList<User>) :
    RecyclerView.Adapter<AdapterUser.ViewHolder>() {

    val context = context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.textView_nombre
        val mail = view.textView_correo
        val phone = view.textView_telefono
        val direc = view.textView_direccion
        val fnac = view.textView_fn
        val imguser = view.image_foto
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterUser.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(layoutInflater)

    }

    override fun getItemCount(): Int {
        return users.size

    }

    override fun onBindViewHolder(holder: AdapterUser.ViewHolder, position: Int) {
        val user : User = users[position]
        var imagen = Utils.getImage(user.img)
        holder.name.text = user.nombre
        holder.mail.text = user.correo
        holder.phone.text = user.telefono
        holder.direc.text = user.direccion
        holder.fnac.text = user.fnacimiento
        holder.imguser.setImageBitmap(imagen)

        holder.itemView.setOnClickListener {
            var db = DataBaseHandler(context)
            db.deleteData(user.id)
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)

        }
    }
}


