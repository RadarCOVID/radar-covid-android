package com.indra.contacttracing.common.base

import android.view.View

abstract class BaseBackNavigationActivity : BaseActivity() {

    fun onBackArrowClick(view: View) {
        onBackPressed()
    }

}