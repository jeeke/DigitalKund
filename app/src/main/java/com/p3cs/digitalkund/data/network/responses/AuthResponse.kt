package com.p3cs.digitalkund.data.network.responses

import com.p3cs.digitalkund.data.db.entities.User

data class AuthResponse(
    val isSuccessful : Boolean?,
    val message: String?,
    val user: User?
)