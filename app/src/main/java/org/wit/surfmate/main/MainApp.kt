package org.wit.surfmate.main

import android.app.Application
import org.wit.surfmate.models.SurfspotModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    val surfspots = ArrayList<SurfspotModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Surfspot started")
//        surfspots.add(SurfspotModel("One", "About one..."))
//        surfspots.add(SurfspotModel("Two", "About two..."))
//        surfspots.add(SurfspotModel("Three", "About three..."))
    }
}