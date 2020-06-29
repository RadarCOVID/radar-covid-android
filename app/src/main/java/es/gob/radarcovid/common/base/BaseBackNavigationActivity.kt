package es.gob.radarcovid.common.base

import android.view.View

abstract class BaseBackNavigationActivity : BaseActivity() {

    open fun onBackArrowClick(view: View) {
        hideKeyBoard()
        onBackPressed()
    }

}