package com.indra.coronaradar.features.onboarding.pages.legal.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.indra.coronaradar.R
import kotlinx.android.synthetic.main.activity_legal_info_detail.*

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

        initViews()
    }

    private fun initViews() {
        buttonAccept.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
        buttonCancel.setOnClickListener { finish() }
    }

}
