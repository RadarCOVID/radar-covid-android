package es.gob.radarcovid.features.mydata.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        textViewConditions.setOnClickListener { presenter.onConditionsButtonClick() }
        textViewPrivacy.setOnClickListener { presenter.onPrivacyButtonClick() }
    }

}