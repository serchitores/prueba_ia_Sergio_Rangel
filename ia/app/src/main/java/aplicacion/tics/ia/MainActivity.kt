package aplicacion.tics.ia

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import aplicacion.tics.ia.Utils.Utils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this
        var db = DataBaseHandler(context)


        button_create_list.setOnClickListener{
            val intent = Intent(this, ListaActivity::class.java)
            startActivity(intent)
        }
        button_create_register.setOnClickListener({

            if (edit_name.text.toString().length > 0 &&
                edit_mail.text.toString().length > 0 &&
                edit_telefono.text.toString().length > 0 &&
                edit_direccion.text.toString().length > 0 &&
                edit_day.text.toString().length > 0 &&
                edit_month.text.toString().length > 0 &&
                edit_year.text.toString().length > 0
            ) {
                val bitman : Bitmap = (image_foto.drawable as BitmapDrawable).bitmap!!
                var user = User()
                user.nombre = edit_name.text.toString()
                user.correo = edit_mail.text.toString()
                user.telefono = edit_telefono.text.toString()
                user.fnacimiento = edit_day.text.toString()+"-"+edit_month.text.toString()+"-"+edit_year.text.toString()
                user.direccion = edit_direccion.text.toString()
                user.img = Utils.getBytes(bitman)
                db.insertData(user)
            } else {
                Toast.makeText(context, "Porfavor ingresa todos los datos", Toast.LENGTH_SHORT)
                    .show()
                edit_name.requestFocus()
                edit_mail.requestFocus()
                edit_telefono.requestFocus()
                edit_day.requestFocus()
                edit_direccion.requestFocus()
                image_foto.requestFocus()

            }
        })


        button_addimg.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    pickImageFromGallery();
                }
            } else {
                pickImageFromGallery();
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        private val IMAGE_PICK_CODE = 1000;
        private val PERMISSION_CODE = 1001;
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            image_foto.setImageURI(data?.data)
        }
    }
}
