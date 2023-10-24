package org.wit.surfmate.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.surfmate.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "surfspots.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<SurfspotModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class SurfspotJSONStore(private val context: Context) : SurfspotStore {

    var surfspots = mutableListOf<SurfspotModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<SurfspotModel> {
        logAll()
        return surfspots
    }

    override fun create(surfspot: SurfspotModel) {
        surfspot.id = generateRandomId()
        surfspots.add(surfspot)
        serialize()
    }


    override fun update(surfspot: SurfspotModel) {
        val surfspotsList = findAll() as ArrayList<SurfspotModel>
        var foundSurfspot: SurfspotModel? = surfspotsList.find { p -> p.id == surfspot.id }
        if (foundSurfspot != null) {
            foundSurfspot.name = surfspot.name
            foundSurfspot.observations = surfspot.observations
            foundSurfspot.image = surfspot.image
            foundSurfspot.lat = surfspot.lat
            foundSurfspot.lng = surfspot.lng
            foundSurfspot.zoom = surfspot.zoom
            foundSurfspot.rating = surfspot.rating

        }
        serialize()
    }
    override fun delete(surfspot: SurfspotModel) {
        surfspots.remove(surfspot)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(surfspots, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        surfspots = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        surfspots.forEach { Timber.i("$it") }
    }

    override fun findById(id:Long) : SurfspotModel? {
        val foundSurfspot: SurfspotModel? = surfspots.find { it.id == id }
        return foundSurfspot
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}