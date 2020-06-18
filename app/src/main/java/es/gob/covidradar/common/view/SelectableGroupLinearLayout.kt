package es.gob.covidradar.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

open class SelectableGroupLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), SelectableGroup {

    var allowMultipleSelection = false

    override fun onChildSelected(view: View) {
        if (allowMultipleSelection) {
            view.isSelected = !view.isSelected
        } else {
            view.isSelected = true
            for (x in 0 until childCount)
                getChildAt(x)?.let {
                    if (it != view)
                        it.isSelected = false
                }
        }
    }

}