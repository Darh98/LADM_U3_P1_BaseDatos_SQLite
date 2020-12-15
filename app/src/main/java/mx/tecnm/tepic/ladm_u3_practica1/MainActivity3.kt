package mx.tecnm.tepic.ladm_u3_practica1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main3.*

class MainActivity3 : AppCompatActivity() {
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        var extras = intent.extras

        descripcionAct.setText(extras!!.getString("desc"))
        fcapturaAct.setText(extras!!.getString("fcap"))
        fentregaAct.setText(extras!!.getString("fent"))

        id = extras.getString("id").toString()

        actualizar.setOnClickListener {
            var c = Actividad(descripcionAct.text.toString(), fcapturaAct.text.toString(), fentregaAct.text.toString())
            c.id = id.toInt()
            c.asignarPuntero(this)

            if(c.actualizar()) {
                Toast.makeText(this,"SE ACTUALIZÓ CORRECTAMENTE LA ACTIVIDAD", Toast.LENGTH_LONG)
                    .show()
                finish()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("ERROR")
                    .setMessage("NO SE PUDO ACTUALIZAR LA ACTIVIDAD")
                    .setPositiveButton("OK"){d,i->}
                    .show()
            }
            finish()
        }
    }
}