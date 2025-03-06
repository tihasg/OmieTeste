package com.fdlr.omieteste

import android.app.Application
import com.fdlr.omieteste.presentation.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class OmieTesteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setUpKoin()
    }

    private fun setUpKoin() {
        startKoin {
            androidLogger()
            androidContext(this@OmieTesteApplication)
            modules(appModule)
        }
    }
}