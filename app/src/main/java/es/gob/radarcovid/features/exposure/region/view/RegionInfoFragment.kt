package es.gob.radarcovid.features.exposure.region.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.view.HintSpinnerAdapter
import es.gob.radarcovid.features.exposure.region.protocols.RegionInfoPresenter
import es.gob.radarcovid.features.exposure.region.protocols.RegionInfoView
import kotlinx.android.synthetic.main.fragment_region_info.*
import javax.inject.Inject

class RegionInfoFragment : BaseFragment(), RegionInfoView {

    companion object {

        fun newInstance(): RegionInfoFragment = RegionInfoFragment()

    }

    @Inject
    lateinit var presenter: RegionInfoPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_region_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.viewReady()
    }

    private fun initViews() {
        wrapperPhone.setOnClickListener { presenter.onPhoneButtonClick() }
        wrapperWeb.setOnClickListener { presenter.onWebButtonClick() }
        spinnerRegion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0)
                    presenter.onRegionSelected()
            }

        }
    }

    override fun setRegions(regions: List<String>) {
        spinnerRegion.adapter =
            HintSpinnerAdapter(
                context!!,
                labelManager.getText(
                    "LOCALE_SELECTION_REGION_DEFAULT",
                    R.string.locale_selection_region_default
                ).toString(),
                R.layout.row_spinner,
                regions
            )
    }

    override fun showRegionInfo(phone: String, web: String) {
        wrapperRegionInfo.visibility = View.VISIBLE
        textViewPhone.text = phone
        textViewWeb.text = web
    }

    override fun getSelectedRegionIndex(): Int =
        spinnerRegion.selectedItemPosition + 1 // POSITION 0 IS THE "NON SELECTED" OPTION

}