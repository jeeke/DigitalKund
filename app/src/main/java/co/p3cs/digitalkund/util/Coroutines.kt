package co.p3cs.digitalkund.util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object Coroutines {

    fun main(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }

    fun io(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.IO).launch {
            work()
        }

    fun <T : Any> ioThenMain(work: suspend (() -> T?), callback: ((T?) -> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async rt@{
                return@rt work()
            }.await()
            callback(data)
        }

    /*
    class MoviesViewModel(
        private val repository: MoviesRepository
    ) : ViewModel() {

        private lateinit var job: Job

        private val _movies = MutableLiveData<List<Movie>>()
        val movies: LiveData<List<Movie>>
            get() = _movies

        fun getMovies() {
            job = Coroutines.ioThenMain(
                { repository.getMovies() },
                { _movies.value = it }
            )
        }

        override fun onCleared() {
            super.onCleared()
            if(::job.isInitialized) job.cancel()
        }
    }

    */

}