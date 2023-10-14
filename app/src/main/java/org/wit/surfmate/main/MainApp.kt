package org.wit.surfmate.main

import android.app.Application
import org.wit.surfmate.models.SurfspotMemStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    val surfspots = SurfspotMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Surfspot started")
    }
}