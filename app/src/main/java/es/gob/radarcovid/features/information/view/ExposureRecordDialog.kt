package es.gob.radarcovid.features.information.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.common.extensions.toDateFormat
import es.gob.radarcovid.datamanager.repository.ExposureRecordRepository
import es.gob.radarcovid.datamanager.utils.LabelManager
import es.gob.radarcovid.models.domain.ExposureInfo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dialog_exposure_record.*
import java.util.*
import javax.inject.Inject

class ExposureRecordDialog(context: Context) : LayoutContainer {

    private val dialog: Dialog

    override val containerView: View =
        LayoutInflater.from(context).inflate(R.layout.dialog_exposure_record, null)

    @Inject
    lateinit var labelManager: LabelManager

    @Inject
    lateinit var exposureRecordRepository: ExposureRecordRepository

    init {

        initializeInjector(context)

        dialog = AlertDialog.Builder(context)
            .setView(containerView)
            .create()
            .apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }

        buttonClose.setOnClickListener { dialog.dismiss() }
        buttonCancel.setOnClickListener { dialog.dismiss() }

    }

    private fun loadRecords() {
        val records = exposureRecordRepository.getExposureRecords()
        var text = ""
        if (records.isEmpty()) {
            text = labelManager.getText(
                "EXPOSURE_RECORDS_NO_DATA",
                R.string.exposure_records_no_data
            ).toString()
        } else {
            records.forEach {
                text += "\u25CF " + it.lastExposureDate.toDateFormat() + "\n"
            }
        }

        recordContent.text = text
    }

    private fun initializeInjector(context: Context) {
        (context.applicationContext as? HasAndroidInjector)?.androidInjector()?.inject(this)
    }

    fun show() {
        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        loadRecords()
    }

}