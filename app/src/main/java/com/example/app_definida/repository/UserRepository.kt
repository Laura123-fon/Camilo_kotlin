package com.example.app_definida.repository

import com.example.app_definida.dao.UserDao
import com.example.app_definida.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    fun getUserByUsername(username: String): Flow<User?> {
        return userDao.getUserByUsername(username)
    }

    fun getUserByEmail(email: String): Flow<User?> {
        return userDao.getUserByEmail(email)
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }
}
