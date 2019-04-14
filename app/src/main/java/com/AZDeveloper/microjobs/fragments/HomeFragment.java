package com.AZDeveloper.microjobs.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.AZDeveloper.microjobs.AyetstudiosActivity;
import com.AZDeveloper.microjobs.IronSourceActivity;
import com.AZDeveloper.microjobs.R;
import com.AZDeveloper.microjobs.Temporary;
import com.google.firebase.auth.FirebaseAuth;
import com.ironsource.adapters.supersonicads.SupersonicConfig;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.OfferwallListener;
import com.tapjoy.TJActionRequest;
import com.tapjoy.TJConnectListener;
import com.tapjoy.TJError;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;
import com.tapjoy.TJSetUserIDListener;
import com.tapjoy.Tapjoy;
import com.tapjoy.TapjoyConnectFlag;

import java.util.Hashtable;

public class HomeFragment extends Fragment implements OfferwallListener, TJPlacementListener, TJConnectListener {

    String APP_KEY = "8b109b75";
    Button mOfferwallButton, tapjoyBtn, ayetBtn;
    View view;

    TJPlacement placement;

    HomeFragment ctx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, null);

        tapjoyBtn = view.findViewById(R.id.home_btn_tapjoy);
        ayetBtn = view.findViewById(R.id.home_btn_ayet);
        ayetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AyetstudiosActivity.class);
                startActivity(intent);
            }
        });
        ///////////Tap Joy////////////////
       ctx = this;

        Tapjoy.setDebugEnabled(true);

        Hashtable<String,Object> connectFlags = new Hashtable<String,Object>();
        connectFlags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");

        Tapjoy.connect(getActivity(), "CyPEXhpET9qPqUC5zB6-qQECff3Kca8Afp4KMBaHl2vyVXY2lbpt8wgO6mhr", connectFlags,  this);



        Tapjoy.setActivity(getActivity());

        tapjoyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placement.requestContent();
            }
        });





        ///////////// Iron Source ///////////////////



        //The integrationHelper is used to validate the integration. Remove the integrationHelper before going live!
        IntegrationHelper.validateIntegration(getActivity());

        startIronSourceInitTask();

        mOfferwallButton = (Button) view.findViewById(R.id.home_btn_irnsrc);
        mOfferwallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show the offerwall
                if (IronSource.isOfferwallAvailable())
                    IronSource.showOfferwall();
            }
        });

        //Network Connectivity Status
        IronSource.shouldTrackNetworkState(getActivity(), true);

        return view;
    }

    private void startIronSourceInitTask(){

        // getting advertiser id should be done on a background thread
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return IronSource.getAdvertiserId(getActivity());
            }
            @Override
            protected void onPostExecute(String advertisingId) {
                if (TextUtils.isEmpty(advertisingId)) {
                    advertisingId = FirebaseAuth.getInstance().getUid();
                }
                // we're using an advertisingId as the 'userId'
                initIronSource(APP_KEY, advertisingId);
            }
        };
        task.execute();
    }

    private void initIronSource(String appKey, String userId) {
        // Be sure to set a listener to each product that is being initiated

        // set the IronSource offerwall listener
        IronSource.setOfferwallListener(this);
        // set client side callbacks for the offerwall
        SupersonicConfig.getConfigObj().setClientSideCallbacks(true);

        IronSource.setUserId(FirebaseAuth.getInstance().getUid());

        // init the IronSource SDK
        IronSource.init(getActivity(), appKey, IronSource.AD_UNIT.OFFERWALL);

        updateButtonsState();

        // In order to work with IronSourceBanners you need to add Providers who support banner ad unit and uncomment next line
        // createAndloadBanner();
    }

    private void updateButtonsState() {

        handleOfferwallButtonState(IronSource.isOfferwallAvailable());
    }

    /**
     * Set the Rewareded Video button state according to the product's state
     *
     * @param available if the offerwall is available
     */
    public void handleOfferwallButtonState(final boolean available) {
        final String text;
        final int color;
        if (available) {
            color = Color.BLUE;
            text = "Available";
        } else {
            color = Color.BLACK;
            text = "Not Available";
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mOfferwallButton.setTextColor(color);
                mOfferwallButton.setText(text);
                mOfferwallButton.setEnabled(available);

            }
        });

    }



    @Override
    public void onResume() {
        super.onResume();
        IronSource.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        IronSource.onPause(getActivity());
    }

    @Override
    public void onOfferwallAvailable(boolean b) {
        handleOfferwallButtonState(b);
    }

    @Override
    public void onOfferwallOpened() {

    }

    @Override
    public void onOfferwallShowFailed(IronSourceError ironSourceError) {
        Toast.makeText(getContext(), "onOfferwallShowFailed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOfferwallAdCredited(int i, int i1, boolean b) {
        Toast.makeText(getActivity(),"onOfferwallAdCredited" + " credits:" + i + " totalCredits:" + i1 + " totalCreditsFlag:" + b, Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onGetOfferwallCreditsFailed(IronSourceError ironSourceError) {
        Toast.makeText(getActivity(), ironSourceError.getErrorMessage(), Toast.LENGTH_LONG).show();

    }


    @Override
    public void onOfferwallClosed() {

    }






    ///////////Tap Joy////////////////

    //session start
    @Override
    public void onStart() {
        super.onStart();
        Tapjoy.onActivityStart(getActivity());
    }
    //session end
    @Override
    public void onStop() {
        Tapjoy.onActivityStop(getActivity());
        super.onStop();
    }













    @Override
    public void onConnectSuccess() {
        Tapjoy.setUserID(FirebaseAuth.getInstance().getUid(), new TJSetUserIDListener() {
            @Override
            public void onSetUserIDSuccess() {
                placement = Tapjoy.getPlacement("Wall", ctx);
                //placement.requestContent();
                //Toast.makeText(getActivity(), Tapjoy.getUserToken(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSetUserIDFailure(String s) {
                Toast.makeText(getActivity(), "FAILED", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onConnectFailure() {
        Toast.makeText(getContext(), "onContentFailure", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestSuccess(TJPlacement tjPlacement) {

    }

    @Override
    public void onRequestFailure(TJPlacement tjPlacement, TJError tjError) {
        Toast.makeText(getContext(), tjError.message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onContentReady(TJPlacement tjPlacement) {
        placement.showContent();
    }

    @Override
    public void onContentShow(TJPlacement tjPlacement) {

    }

    @Override
    public void onContentDismiss(TJPlacement tjPlacement) {
        Toast.makeText(getContext(), "Content Dismiss", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPurchaseRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s) {

    }

    @Override
    public void onRewardRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s, int i) {

    }
}
