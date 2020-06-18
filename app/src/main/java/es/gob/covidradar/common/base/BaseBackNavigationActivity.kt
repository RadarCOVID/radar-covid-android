package es.gob.covidradar.common.base

import android.view.View

abstract class BaseBackNavigationActivity : BaseActivity() {

    open fun onBackArrowClick(view: View) {
        onBackPressed()
    }

}