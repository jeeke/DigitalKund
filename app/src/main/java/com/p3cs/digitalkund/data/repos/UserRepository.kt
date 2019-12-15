package com.p3cs.digitalkund.data.repos

import com.p3cs.digitalkund.data.db.AppDatabase
import com.p3cs.digitalkund.data.db.entities.User
import com.p3cs.digitalkund.data.network.MyApi
import com.p3cs.digitalkund.data.network.SafeApiRequest
import com.p3cs.digitalkund.data.network.responses.AuthResponse

class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun userSignup(
        name: String,
        email: String,
        password: String
    ) : AuthResponse {
        return apiRequest{ api.userSignup(name, email, password)}
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getuser()

}