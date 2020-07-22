package es.gob.radarcovid.features.mydata.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.features.mydata.protocols.MyDataPresenter
import es.gob.radarcovid.features.mydata.protocols.MyDataView
import kotlinx.android.synthetic.main.fragment_my_data.*
import javax.inject.Inject


class MyDataFragment : BaseFragment(), MyDataView {

    companion object {
        fun newInstance() = MyDataFragment()
    }

    @Inject
    lateinit var presenter: MyDataPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.viewReady()
    }

    private fun initViews() {
        val text3 = SpannableString(labelManager.getText("MY_DATA_BULLET_3", R.string.my_data_l3))
        text3.setSpan(
            TextAppearanceSpan(context, R.style.TitleExtraBoldPurpleSmall),
            0,
            2,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text3.setSpan(StyleSpan(Typeface.BOLD), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text3.setSpan(
            TextAppearanceSpan(context, R.style.TitleTip),
            2,
            text3.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        dottedTextViewL3.setText(null, text3, TextView.BufferType.SPANNABLE)

        textViewConditions.setOnClickListener { presenter.onConditionsButtonClick() }
        textViewPrivacy.setOnClickListener { presenter.onPrivacyButtonClick() }

    }

}