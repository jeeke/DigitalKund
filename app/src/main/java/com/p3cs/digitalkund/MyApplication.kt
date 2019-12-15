package com.p3cs.digitalkund

import android.app.Application
import com.p3cs.digitalkund.data.db.AppDatabase
import com.p3cs.digitalkund.data.network.MyApi
import com.p3cs.digitalkund.data.network.NetworkConnectionInterceptor
import com.p3cs.digitalkund.data.preferences.PreferenceProvider
import com.p3cs.digitalkund.data.repos.QuotesRepository
import com.p3cs.digitalkund.data.repos.UserRepository
import com.p3cs.digitalkund.ui.auth.AuthViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class MyApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { QuotesRepository(instance(), instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }


    }

}