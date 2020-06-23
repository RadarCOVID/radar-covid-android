package es.gob.radarcovid.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper
import androidx.appcompat.widget.AppCompatEditText

class DeleteDetectionEditText : AppCompatEditText {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var onDeleteButtonClickListener: (() -> Unit)? = null

    override fun onCreateInputConnection(outAttrs: EditorInfo?): InputConnection =
        CustomInputConnection(super.onCreateInputConnection(outAttrs), true)


    inner class CustomInputConnection(target: InputConnection?, mutable: Boolean) :
        InputConnectionWrapper(target, mutable) {
        override fun sendKeyEvent(event: KeyEvent): Boolean {
            if (event.action == KeyEvent.ACTION_DOWN
                && event.keyCode == KeyEvent.KEYCODE_DEL
            ) {
                onDeleteButtonClickListener?.invoke()
            }
            return super.sendKeyEvent(event)
        }
    }

}