package com.murali.jet2travel.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class NetworkStateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener!!.networkConnectionChanged(isConnectedOrConnecting(context!!))
        }
    }

    private fun isConnectedOrConnecting(context: Context): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    interface NetConnectivityReceiverListener {
        fun networkConnectionChanged(isConnected: Boolean)
    }

    companion object {
        var connectivityReceiverListener: NetConnectivityReceiverListener? = null
    }
}