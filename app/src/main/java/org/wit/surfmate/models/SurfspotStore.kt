package org.wit.surfmate.models

interface SurfspotStore {
    fun findAll(): List<SurfspotModel>
    fun create(surfspot: SurfspotModel)
    fun update(surfspot: SurfspotModel)
}