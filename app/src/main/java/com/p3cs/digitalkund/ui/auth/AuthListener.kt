package com.p3cs.digitalkund.ui.auth

import com.p3cs.digitalkund.data.db.entities.User


interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}