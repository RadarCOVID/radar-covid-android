package es.gob.radarcovid.common.view

interface RequestView {

    fun showLoading()

    fun hideLoading()

    fun hideLoadingWithError(error: Throwable)

    fun showError(error: Throwable, finishOnDismiss: Boolean = false)

}