package es.gob.radarcovid.features.covidreport.confirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import es.gob.radarcovid.R

class ConfirmationActivity : AppCompatActivity() {

    companion object {

        fun open(context: Context) {
            context.startActivity(Intent(context, ConfirmationActivity::class.java))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)
    }

    fun onBackArrowClick(view: View) {
        onBackPressed()
    }

}
