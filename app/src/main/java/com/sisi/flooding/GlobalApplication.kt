package com.sisi.flooding

import android.app.Application
import com.sisi.flooding.data.PreferenceUtil

class GlobalApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }
    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}