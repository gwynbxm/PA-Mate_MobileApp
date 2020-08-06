/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.helper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.concurrent.TimeUnit;

/**
 * Created by GBXM on 4/2/2018.
 */

public class MobileWearableService extends WearableListenerService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private boolean nodeConnected = false;
    private int TIMEOUT_S = 60;

    @Override
    public void onCreate() {
        super.onCreate();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        nodeConnected = true;

        sendData("test");
    }

    @Override
    public void onConnectionSuspended(int i) {
        nodeConnected = false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        nodeConnected = false;
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        super.onDataChanged(dataEventBuffer);
        Log.e("WEAR APP", "on data changeddddd");

    }

    private void sendData(final String dataToSend)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                if (!nodeConnected)
                {
                    googleApiClient.blockingConnect(TIMEOUT_S, TimeUnit.SECONDS);
                }
                if (!nodeConnected)
                {
                    Log.e("WEAR APP", "Failed to connect to mGoogleApiClient within " + TIMEOUT_S + " seconds");
                    return;
                }

                if (googleApiClient.isConnected())
                {
                    PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/sample_path");
                    putDataMapRequest.getDataMap().putString("data", dataToSend);
                    PutDataRequest request = putDataMapRequest.asPutDataRequest();

                    PendingResult<DataApi.DataItemResult> pendingResult =
                            Wearable.DataApi.putDataItem(googleApiClient, request);

                    pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>()
                    {
                        @Override
                        public void onResult(DataApi.DataItemResult dataItemResult)
                        {
                            Log.e("WEAR APP", "APPLICATION Result has come");
                        }
                    });

                } else {
                    Log.e("WEAR APP", "No Google API Client connection");
                }
            }
        }).start();
    }

}
