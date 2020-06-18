package es.gob.covidradar.common.view

interface RequestView {

    fun showLoading()

    fun hideLoading()

    fun showError(error: Throwable, finishOnDismiss: Boolean = false)
    
}