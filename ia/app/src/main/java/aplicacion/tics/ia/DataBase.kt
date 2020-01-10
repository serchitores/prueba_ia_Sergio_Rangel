package aplicacion.tics.ia

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

val DATABASE_NAME = "MyDB"
val COL_ID = "id"
val TABLE_NAME = "Users"
val COL_NAME = "name"
val COL_MAIL = "mail"
val COL_PHONE = "phone"
val COL_ADDRESS = "address"
val COL_DATE = "date"
val COL_IMG = "img"


class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " TEXT," +
                COL_MAIL + " TEXT," +
                COL_PHONE + " TEXT," +
                COL_IMG + " BLOB," +
                COL_ADDRESS + " TEXT," +
                COL_DATE + " TEXT)"

        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertData(user: User) {
        var conten = ContentValues()
        conten.put(COL_NAME, user.nombre)
        conten.put(COL_MAIL, user.correo)
        conten.put(COL_PHONE, user.telefono)
        conten.put(COL_ADDRESS, user.direccion)
        conten.put(COL_DATE, user.fnacimiento)
        conten.put(COL_IMG, user.img)
        val db = this.writableDatabase
        try {
            db.insert(TABLE_NAME, null, conten)
            Toast.makeText(context, "Usuario creado", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Problemas al crear usuario" + e, Toast.LENGTH_SHORT).show()
        }
    }

    fun readData(): ArrayList<User> {
        var list: ArrayList<User> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME + " ORDER BY id COLLATE NOCASE DESC"
        val result = db.rawQuery(query, null)

        if (result.count == 0)
            Toast.makeText(context, "No hay datos registrados", Toast.LENGTH_LONG).show()
        else {
            while (result.moveToNext()) {
                var user = User()
                user.id = result.getInt(result.getColumnIndex(COL_ID))
                user.nombre = result.getString(result.getColumnIndex(COL_NAME))
                user.correo = result.getString(result.getColumnIndex(COL_MAIL))
                user.telefono = result.getString(result.getColumnIndex(COL_PHONE))
                user.img = result.getBlob(result.getColumnIndex(COL_IMG))
                user.direccion = result.getString(result.getColumnIndex(COL_ADDRESS))
                user.fnacimiento = result.getString(result.getColumnIndex(COL_DATE))
                list.add(user)
            }
            Toast.makeText(context, "obteniendo datos", Toast.LENGTH_LONG).show()
        }
        result.close()
        db.close()
        return list
    }

    fun deleteData(id: Int): Boolean {
        val query = "DELETE FROM $TABLE_NAME WHERE $COL_ID = $id "
        val db = this.writableDatabase
        var result : Boolean = false
        try {
            val cursor : Unit = db.execSQL(query)
            result = true
        }catch (e: Exception){
            Log.e(ContentValues.TAG, "Error al eliminar")
        }
        db.close()
        return result
    }

}