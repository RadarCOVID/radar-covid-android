package com.indra.contacttracing.common.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.indra.contacttracing.R
import kotlinx.android.synthetic.main.spinner_dialog.*

class TransparentProgressDialog(context: Context) : Dialog(context, R.style.FloatingDialog) {

    init {
        setCancelable(false)
        setOnCancelListener(null)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spinner_dialog)
        Glide.with(context)
            .load(R.raw.light_spinner)
            .into(DrawableImageViewTarget(spinnerView))

    }

}