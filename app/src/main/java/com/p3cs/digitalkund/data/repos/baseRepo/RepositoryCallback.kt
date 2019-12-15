package com.p3cs.digitalkund.data.repos.baseRepo

interface RepositoryCallback<T> {
    fun onSuccess(result: List<T>)
    fun onError(msg: String)
}