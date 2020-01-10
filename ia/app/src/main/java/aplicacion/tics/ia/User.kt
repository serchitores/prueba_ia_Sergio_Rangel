package aplicacion.tics.ia

import android.graphics.Bitmap
import java.sql.Blob

class User {
    var id: Int = 0
    lateinit var img: ByteArray
    var nombre: String = ""
    var correo: String = ""
    var telefono: String = ""
    var direccion: String = ""
    var fnacimiento: String = ""

    constructor(nombre: String, correo: String, telefono: String, fnacimiento: String, direccion:String, img:ByteArray) {
        this.nombre = nombre
        this.correo = correo
        this.telefono = telefono
        this.fnacimiento = fnacimiento
        this.img = img
        this.direccion= direccion
    }
    constructor(){
    }
}