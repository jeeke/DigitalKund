package co.p3cs.digitalkund.ui.auth


interface AuthListener {
    fun onStarted()
    fun onSuccess(message: String)
    fun onFailure(message: String)
}