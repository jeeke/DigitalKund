package com.p3cs.digitalkund.data.network.responses

import com.p3cs.digitalkund.data.db.entities.Quote


data class QuotesResponse (
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)