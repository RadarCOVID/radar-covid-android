package es.gob.covidradar.features.health.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.covidradar.R
import es.gob.covidradar.common.base.BaseFragment
import es.gob.covidradar.features.health.protocols.HealthPresenter
import es.gob.covidradar.features.health.protocols.HealthView
import javax.inject.Inject

class HealthFragment : BaseFragment(), HealthView {

    companion object {

        fun newInstance() = HealthFragment()

    }

    @Inject
    lateinit var presenter: HealthPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_health, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.viewReady()
    }
}