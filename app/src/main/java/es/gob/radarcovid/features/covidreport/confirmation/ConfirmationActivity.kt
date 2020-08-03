package es.gob.radarcovid.features.covidreport.confirmation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_confirmation.*

class ConfirmationActivity : BaseActivity() {

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
        val infoUrl =
            labelManager.getText("MY_HEALTH_REPORTED_INFO_URL", R.string.exposure_detail_info_url)
                .toString()
        val infoUri: Uri = if (infoUrl.contains("http://") || infoUrl.contains("https://"))
            Uri.parse(infoUrl)
        else
            Uri.parse("http://$infoUrl")
        buttonMoreInfo.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = infoUri
            })
        }
    }

}
