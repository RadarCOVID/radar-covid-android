package es.gob.radarcovid.features.locale.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionPresenter
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionView
import javax.inject.Inject

class LocaleSelectionFragment : BaseFragment(), LocaleSelectionView {

    companion object {
        fun newInstance() = LocaleSelectionFragment
    }

    @Inject
    lateinit var presenter: LocaleSelectionPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_locale_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.viewReady()
    }

}