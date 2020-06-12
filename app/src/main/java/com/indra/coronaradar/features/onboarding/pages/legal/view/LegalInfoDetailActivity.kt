package com.indra.coronaradar.features.onboarding.pages.legal.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.indra.coronaradar.R

class LegalInfoDetailActivity : AppCompatActivity() {

    companion object {

        const val REQUEST_CODE_LEGAL_TERMS = 1

        fun openForResult(fragment: Fragment) {
            fragment.startActivityForResult(
                Intent(
                    fragment.context!!,
                    LegalInfoDetailActivity::class.java
                ), REQUEST_CODE_LEGAL_TERMS
            )
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legal_info_detail)
    }

}
