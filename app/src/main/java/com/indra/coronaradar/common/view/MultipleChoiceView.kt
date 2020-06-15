package com.indra.coronaradar.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.indra.coronaradar.R
import kotlinx.android.synthetic.main.view_multiple_choice_item.view.*

class MultipleChoiceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SelectableGroupLinearLayout(context, attrs, defStyleAttr) {
    init {

        orientation = VERTICAL

        allowMultipleSelection = true

        addView(MultipleChoiceItemView(context).apply {
            setText("Si")
        })

        addView(MultipleChoiceItemView(context).apply {
            setText("No")
        })

    }

    class MultipleChoiceItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : LinearLayout(context, attrs, defStyleAttr) {
        init {
            LayoutInflater.from(context).inflate(R.layout.view_multiple_choice_item, this)
            wrapperContent.setOnClickListener { (parent as? SelectableGroup)?.onChildSelected(this) }
        }

        override fun setSelected(selected: Boolean) {
            super.setSelected(selected)
            if (selected)
                imageViewCheck.visibility = View.VISIBLE
            else
                imageViewCheck.visibility = View.GONE
        }

        fun setText(text: String) {
            textViewAnswer.text = text
        }
    }

}