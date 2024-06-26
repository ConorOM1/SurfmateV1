package org.wit.surfmate.models

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList
import java.lang.reflect.Type
import com.google.gson.*
import org.wit.surfmate.helpers.*
import timber.log.Timber

const val JSON_FILE2 = "users.json"
val gsonBuilder2: Gson = GsonBuilder().setPrettyPrinting()
    .create()
val listType2: Type = object : TypeToken<ArrayList<UserModel>>() {}.type

fun generateRandomUserId(): Long {
    return Random().nextLong()
}

class UserJSONStore(private val context: Context) : UserStore {
    var users = mutableListOf<UserModel>()

    init {
        if (exists(context, JSON_FILE2)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<UserModel> {
        logAll()
        return users
    }

    override fun create(user: UserModel) : UserModel {
        user.userId = generateRandomUserId()
        users.add(user)
        serialize()
        return user
    }

    override fun login(userEmail: String, userPassword: String): UserModel? {
        val users = findAll()
        for (user in users) {
            if (user.userEmail == userEmail && user.userPassword == userPassword) {
                return user
            }
        }
        return null
    }

    override fun update(user: UserModel) {
        var foundUser: UserModel? = users.find { u -> u.userId == user.userId }
        if (foundUser != null) {
            foundUser.firstName = user.firstName
            foundUser.lastName = user.lastName
            foundUser.userEmail = user.userEmail
            foundUser.userPassword = user.userPassword
            logAll()
        }
        serialize()
    }

    override fun delete(user: UserModel) {
        users.remove(user)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder2.toJson(users, listType2)
        write(context, JSON_FILE2, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE2)
        users = gsonBuilder2.fromJson(jsonString, listType2)
    }

    private fun logAll() {
        users.forEach { Timber.i("$it") }
    }
}
