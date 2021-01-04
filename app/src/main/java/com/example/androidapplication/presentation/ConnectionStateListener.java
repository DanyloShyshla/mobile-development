package com.example.androidapplication.presentation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

public class ConnectionStateListener extends LiveData<Boolean> {

    private final ConnectivityManager.NetworkCallback networkCallback;
    private final ConnectivityManager connectivityManager;

    public ConnectionStateListener(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkCallback = new NetworkCallback(this);
    }

    @Override
    protected void onActive() {
        super.onActive();
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

    static class NetworkCallback extends ConnectivityManager.NetworkCallback {

        private final ConnectionStateListener mConnectionStateMonitor;

        public NetworkCallback(ConnectionStateListener connectionStateMonitor) {
            mConnectionStateMonitor = connectionStateMonitor;
        }

        @Override
        public void onAvailable(@NotNull Network network) {
            mConnectionStateMonitor.postValue(true);
        }

        @Override
        public void onLost(@NotNull Network network) {
            mConnectionStateMonitor.postValue(false);
        }
    }

}