package es.gob.radarcovid.features.covidreport.confirmation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import es.gob.radarcovid.R
import kotlinx.android.synthetic.main.activity_confirmation.*

class ConfirmationActivity : AppCompatActivity() {

    companion object {

        fun open(context: Context) {
            context.startActivity(Intent(context, ConfirmationActivity::class.java))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        initViews()
    }

    fun onBackArrowClick(view: View) {
        onBackPressed()
    }

    private fun initViews() {
        buttonMoreInfo.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://www.mscbs.gob.es/profesionales/saludPublica/ccayes/alertasActual/nCov-China/ciudadania.htm")
            })
        }
    }

}
