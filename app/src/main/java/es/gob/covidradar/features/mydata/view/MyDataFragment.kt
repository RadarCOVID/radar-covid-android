package es.gob.covidradar.features.mydata.view

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
import es.gob.covidradar.R
import es.gob.covidradar.common.base.BaseFragment
import es.gob.covidradar.features.mydata.protocols.MyDataPresenter
import es.gob.covidradar.features.mydata.protocols.MyDataView
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
//        val text1 = SpannableString(getString(R.string.my_data_l1))
//        text1.setSpan(StyleSpan(Typeface.BOLD), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        text1.setSpan(
//            TextAppearanceSpan(context, R.style.TitleExtraBoldSmall),
//            0,
//            2,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//        text1.setSpan(StyleSpan(Typeface.BOLD), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        text1.setSpan(
//            TextAppearanceSpan(context, R.style.TextRegular),
//            2,
//            text1.length,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//        dottedTextViewL1.setText(text1, TextView.BufferType.SPANNABLE)
//
//        val text2 = SpannableString(getString(R.string.my_data_l2))
//        text2.setSpan(
//            TextAppearanceSpan(context, R.style.TitleExtraBoldSmall),
//            0,
//            2,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//        text2.setSpan(StyleSpan(Typeface.BOLD), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        text2.setSpan(
//            TextAppearanceSpan(context, R.style.TextRegular),
//            2,
//            text2.length,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//        dottedTextViewL2.setText(text2, TextView.BufferType.SPANNABLE)

        val text3 = SpannableString(getString(R.string.my_data_l3))
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
        dottedTextViewL3.setText(text3, TextView.BufferType.SPANNABLE)

    }

}