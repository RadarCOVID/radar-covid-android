package com.indra.coronaradar.features.covidreport.confirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.indra.coronaradar.R

class Confirmation : AppCompatActivity() {

    companion object {

        fun open(context: Context) {
            context.startActivity(Intent(context, Confirmation::class.java))
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
