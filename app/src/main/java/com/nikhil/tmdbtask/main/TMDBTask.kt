package com.nikhil.motialoswaltask.main

import android.app.Application
import com.facebook.stetho.Stetho

class TMDBTask:Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

    }
}