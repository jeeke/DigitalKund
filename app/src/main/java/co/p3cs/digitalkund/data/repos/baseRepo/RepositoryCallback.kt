package co.p3cs.digitalkund.data.repos.baseRepo

interface RepositoryCallback<T> {
    fun onSuccess(result: List<T>)
    fun onSuccess()
    fun onError(msg: String)
}