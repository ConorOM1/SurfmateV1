package org.wit.surfmate.models

import timber.log.Timber.Forest.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class SurfspotMemStore : SurfspotStore {

    val surfspots = ArrayList<SurfspotModel>()

    override fun findAll(): List<SurfspotModel> {
        return surfspots
    }

    override fun create(surfspot: SurfspotModel) {
        surfspot.id = getId()
        surfspots.add(surfspot)
        logAll()
    }

    override fun update(surfspot: SurfspotModel) {
        var foundSurfspot: SurfspotModel? = surfspots.find { p -> p.id == surfspot.id }
        if (foundSurfspot != null) {
            foundSurfspot.title = surfspot.title
            foundSurfspot.description = surfspot.description
            foundSurfspot.image = surfspot.image
            foundSurfspot.lat = surfspot.lat
            foundSurfspot.lng = surfspot.lng
            foundSurfspot.zoom = surfspot.zoom
            logAll()
        }
    }

    override fun delete(surfspot: SurfspotModel) {
        surfspots.remove(surfspot)
    }

    private fun logAll() {
        surfspots.forEach { i("$it") }
    }
}