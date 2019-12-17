package co.p3cs.digitalkund

import android.app.Application
import co.p3cs.digitalkund.data.repos.AuthRepository
import co.p3cs.digitalkund.ui.auth.AuthViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class MyApplication : Application(), KodeinAware {

    private var mInstance: MyApplication? = null

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    @Synchronized
    fun getInstance(): MyApplication? {
        return mInstance
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))
        bind() from singleton { AuthRepository() }
        bind() from provider { AuthViewModelFactory(instance(),instance()) }
    }

}