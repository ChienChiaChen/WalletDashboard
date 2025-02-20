package com.exebrain.walletdashboard

import android.app.Application
import com.exebrain.walletdashboard.utils.JsonUtils
import com.exebrain.walletdashboard.utils.NetUtils

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        JsonUtils.init(context = this@App)
        NetUtils.init(context = this@App)
    }
}