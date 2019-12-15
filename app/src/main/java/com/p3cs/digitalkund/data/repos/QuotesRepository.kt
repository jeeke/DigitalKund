package com.p3cs.digitalkund.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.p3cs.digitalkund.data.db.AppDatabase
import com.p3cs.digitalkund.data.db.entities.Quote
import com.p3cs.digitalkund.data.network.MyApi
import com.p3cs.digitalkund.data.network.SafeApiRequest
import com.p3cs.digitalkund.data.preferences.PreferenceProvider
import com.p3cs.digitalkund.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.*

private val MINIMUM_INTERVAL = 6

class QuotesRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            saveQuotes(it)
        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes() {
        val lastSavedAt = prefs.getLastSavedAt()

        if(true){
//        if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
            try {
                val response = apiRequest { api.getQuotes() }
                quotes.postValue(response.quotes)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return true
//        return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }


    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {
            prefs.savelastSavedAt(Date().toString())
//            prefs.savelastSavedAt(LocalDateTime.now().toString())
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }

}