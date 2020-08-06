package es.gob.radarcovid.common.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import es.gob.radarcovid.R


object CMDialog {

    class Builder(private val context: Context) {

        private val view: View =
            LayoutInflater.from(context).inflate(R.layout.dialog, null)
        private val textViewTitle = view.findViewById<TextView>(R.id.textViewDialogTitle)
        private val textViewDescription =
            view.findViewById<TextView>(R.id.textViewDialogDescription)
        private val buttonOk = view.findViewById<Button>(R.id.buttonOk)
        private val buttonCancel = view.findViewById<Button>(R.id.buttonCancel)
        private val buttonClose = view.findViewById<Button>(R.id.buttonClose)
        private val dialog: AlertDialog = AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(false)
            .create().apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }

        fun setTitle(resId: Int): Builder = setTitle(context.getString(resId))

        fun setTitle(text: String): Builder {
            textViewTitle.visibility = View.VISIBLE
            textViewTitle.text = text
            return this
        }

        fun setMessage(resId: Int): Builder = setMessage(context.getString(resId))

        fun setMessage(text: String): Builder {
            textViewDescription.visibility = View.VISIBLE
            textViewDescription.text = text
            return this
        }

        fun setCloseButton(onCloseButtonClick: ((AlertDialog) -> Unit)): Builder {
            buttonClose.visibility = View.VISIBLE
            buttonClose.setOnClickListener {
                onCloseButtonClick(dialog)
            }
            return this
        }

        fun setPositiveButton(
            resId: Int,
            onPositiveButtonClick: ((AlertDialog) -> Unit)
        ): Builder = setPositiveButton(context.getString(resId), onPositiveButtonClick)

        fun setPositiveButton(
            text: String,
            onPositiveButtonClick: ((AlertDialog) -> Unit)
        ): Builder {
            buttonOk.visibility = View.VISIBLE
            buttonOk.text = text
            buttonOk.setOnClickListener {
                onPositiveButtonClick(dialog)
            }
            return this
        }

        fun setNegativeButton(
            resId: Int,
            onNegativeButtonClick: ((AlertDialog) -> Unit)
        ): Builder = setNegativeButton(context.getString(resId), onNegativeButtonClick)

        fun setNegativeButton(
            text: String,
            onNegativeButtonClick: ((AlertDialog) -> Unit)
        ): Builder {
            buttonCancel.visibility = View.VISIBLE
            buttonCancel.text = text
            buttonCancel.setOnClickListener {
                onNegativeButtonClick(dialog)
            }
            return this
        }

        fun build(): AlertDialog = dialog

    }

}