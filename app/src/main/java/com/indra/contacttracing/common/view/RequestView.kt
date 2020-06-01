package com.indra.contacttracing.common.view

interface RequestView {

    fun showLoading()

    fun hideLoading()

    fun showError(error: Throwable)
    
}