package org.wit.surfmate.main

import android.app.Application
import org.wit.surfmate.models.SurfspotJSONStore
import org.wit.surfmate.models.SurfspotMemStore
import org.wit.surfmate.models.SurfspotStore
import org.wit.surfmate.models.UserJSONStore
import org.wit.surfmate.models.UserMemStore
import org.wit.surfmate.models.UserStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    lateinit var surfspots : SurfspotStore
    lateinit var users: UserStore


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        surfspots = SurfspotJSONStore(applicationContext)
        users = UserJSONStore(applicationContext)
        i("Surfspot started")
    }
}