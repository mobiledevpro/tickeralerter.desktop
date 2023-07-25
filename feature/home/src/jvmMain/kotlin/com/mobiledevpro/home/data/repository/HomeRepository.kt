package com.mobiledevpro.home.data.repository

interface HomeRepository {
    suspend fun getServerTime(): Long
}