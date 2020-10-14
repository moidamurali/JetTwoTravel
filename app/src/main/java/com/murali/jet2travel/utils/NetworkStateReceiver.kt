package com.murali.jet2travel.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class NetworkStateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener!!.networkConnectionChanged(Constants.hasNetworkConnected(context!!))
        }
    }

    interface NetConnectivityReceiverListener {
        fun networkConnectionChanged(isConnected: Boolean)
    }

    companion object {
        var connectivityReceiverListener: NetConnectivityReceiverListener? = null
    }
}