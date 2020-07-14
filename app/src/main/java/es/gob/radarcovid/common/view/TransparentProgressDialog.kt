package es.gob.radarcovid.common.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import es.gob.radarcovid.R
import kotlinx.android.synthetic.main.spinner_dialog.*

class TransparentProgressDialog(context: Context) :
    Dialog(context, R.style.FloatingDialog) {

    init {
        setCancelable(false)
        setOnCancelListener(null)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private val fadeInDuration: Long = 200
    private val fadeOutDuration: Long = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spinner_dialog)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        viewBackground.startAnimation(AlphaAnimation(0f, viewBackground.alpha).apply {
            duration = fadeInDuration
        })
    }

    override fun hide() {
        viewBackground.startAnimation(AlphaAnimation(viewBackground.alpha, 0f).apply {
            duration = fadeOutDuration
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {

                }

                override fun onAnimationEnd(animation: Animation?) {
                    super@TransparentProgressDialog.hide()
                }

                override fun onAnimationStart(animation: Animation?) {

                }
            })
        })
    }
}