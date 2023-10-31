package org.wit.surfmate.models

import timber.log.Timber.Forest.i

var lastUserId = 0L

internal fun getUserId(): Long {
    return lastUserId++
}

class UserMemStore : UserStore {

    val users = ArrayList<UserModel>()

    override fun findAll(): List<UserModel> {
        return users
    }

    override fun create(user: UserModel) : UserModel {
        user.userId = getId()
        users.add(user)
        logAll()
        return user
    }

    override fun login(userEmail: String, userPassword: String): UserModel? {
        return users.find { it.userEmail == userEmail && it.userPassword == userPassword }
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
    }

    override fun delete(user: UserModel) {
        users.remove(user)
        logAll()
    }

    fun logAll() {
        users.forEach { i("${it}") }
    }
}
