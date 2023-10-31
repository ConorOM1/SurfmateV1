package org.wit.surfmate.models
interface UserStore {

    fun findAll(): List<UserModel>
    fun create(user: UserModel) : UserModel
    fun update(user: UserModel)
    fun delete(user: UserModel)
    fun login(userEmail: String, userPassword: String): UserModel?
}