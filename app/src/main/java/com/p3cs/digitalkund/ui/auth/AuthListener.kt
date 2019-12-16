package com.p3cs.digitalkund.ui.auth


interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}