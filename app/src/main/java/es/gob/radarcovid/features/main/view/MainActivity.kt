package es.gob.radarcovid.features.main.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseActivity
import es.gob.radarcovid.common.view.CMDialog
import es.gob.radarcovid.features.main.protocols.MainPresenter
import es.gob.radarcovid.features.main.protocols.MainView
import es.gob.radarcovid.features.poll.main.view.PollActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.dpppt.android.sdk.DP3T
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    companion object {

        const val EXTRA_ACTIVATE_RADAR = "extra_activate_radar"

        fun open(context: Context, activateRadar: Boolean) =
            context.startActivity(Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_ACTIVATE_RADAR, activateRadar)
            })

    }

    @Inject
    lateinit var presenter: MainPresenter

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        DP3T.onActivityResult(this, requestCode, resultCode, data)
        if (requestCode == PollActivity.REQUEST_CODE_POLL_COMPLETED && resultCode == Activity.RESULT_OK)
            presenter.onPollCompleted()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        presenter.viewReady(intent.getBooleanExtra(EXTRA_ACTIVATE_RADAR, false))
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    private fun initViews() {
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuItemHome -> presenter.onHomeButtonClick()
//                R.id.menuItemHealth -> presenter.onHealthButtonClick()
                R.id.menuItemProfile -> presenter.onProfileButtonClick()
                R.id.menuItemHelpline -> presenter.onHelplineButtonClick()
            }
            true
        }

    }

    override fun onBackPressed() {
        if (bottomNavigation.selectedItemId == R.id.menuItemHome)
            presenter.onBackPressed()
        else
            bottomNavigation.selectedItemId = R.id.menuItemHome
    }

    override fun showExitConfirmationDialog() {
        CMDialog.createDialog(
            this, "",
            getString(R.string.warning_exit_application_message),
            getString(R.string.warning_exit_application_button), null
        ) {
            presenter.onExitConfirmed()
        }.show()
    }
}
