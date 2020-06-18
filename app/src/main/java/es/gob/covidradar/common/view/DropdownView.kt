package es.gob.covidradar.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import es.gob.covidradar.R
import kotlinx.android.synthetic.main.view_dropdown.view.*

class DropdownView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_dropdown, this)

        var title = ""
        var content = ""

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.DropdownView,
            0, 0
        ).apply {

            try {
                title = getString(R.styleable.DropdownView_title) ?: ""
                content = getString(R.styleable.DropdownView_dropdownText) ?: ""
            } finally {
                recycle()
            }
        }

        initViews(context, title, content)
    }

    private fun initViews(context: Context, title: String, content: String) {
        wrapperHeader.setOnClickListener {
            if (textViewDropdownContent.isVisible) {
                textViewDropdownTitle.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.purple_9C
                    )
                )
                imageViewDropdown.setImageResource(R.drawable.ic_dropdown_closed)
                textViewDropdownContent.visibility = View.GONE
            } else {
                textViewDropdownTitle.setTextColor(ContextCompat.getColor(context, R.color.black))
                imageViewDropdown.setImageResource(R.drawable.ic_dropdown_opened)
                textViewDropdownContent.visibility = View.VISIBLE
            }
        }

        textViewDropdownTitle.text = title
        textViewDropdownContent.text = content
    }

}