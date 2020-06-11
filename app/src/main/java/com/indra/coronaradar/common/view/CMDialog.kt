package com.indra.coronaradar.common.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.indra.coronaradar.R


object CMDialog {

    fun createDialog(
        context: Context,
        title: Int,
        description: Int,
        buttonText: Int,
        onCloseButtonClick: (() -> Unit)?,
        onButtonClick: () -> Unit
    ): AlertDialog = createDialog(
        context,
        context.getString(title),
        context.getString(description),
        context.getString(buttonText),
        onCloseButtonClick,
        onButtonClick
    )

    fun createDialog(
        context: Context,
        title: String,
        description: String,
        buttonText: String,
        onCloseButtonClick: (() -> Unit)?,
        onButtonClick: () -> Unit
    ): AlertDialog {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.dialog, null)
        val textViewTitle = view.findViewById<TextView>(R.id.textViewDialogTitle)
        val textViewDescription = view.findViewById<TextView>(R.id.textViewDialogDescription)
        val button = view.findViewById<Button>(R.id.button)
        val buttonClose = view.findViewById<Button>(R.id.buttonClose)

        textViewTitle.text = title
        textViewDescription.text = description

        val dialog = AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(false)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        buttonClose.setOnClickListener {
            onCloseButtonClick?.invoke()
            dialog.dismiss()
        }
        button.text = buttonText
        button.setOnClickListener {
            onButtonClick.invoke()
            dialog.dismiss()
        }
        return dialog
    }

}